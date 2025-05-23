package org.aoclient.engine.listeners;

import imgui.ImGui;
import imgui.ImGuiIO;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Clase que gestiona todos los eventos y estados del mouse en el contexto GLFW.
 * <p>
 * Proporciona funciones callback para GLFW que permiten detectar movimientos del cursor, pulsaciones de botones y desplazamiento
 * de la rueda del mouse.
 * <p>
 * Esta clase mantiene un registro constante de la posicion actual del mouse, su posicion anterior, el estado de los botones y
 * detecta acciones como: arrastrar elementos, clic simple, doble clic y desplazamiento. Proporciona funcionalidad para determinar
 * si el mouse esta sobre areas especificas y gestiona temporizadores para deteccion de doble clic.
 * <p>
 * Todos los demas componentes del juego que necesitan informacion sobre el estado del mouse o responder a eventos del mismo
 * utilizan esta clase a traves de sus metodos estaticos.
 * <p>
 * Tambien se encarga de sincronizar adecuadamente con {@code ImGui} para mantener consistencia en los eventos del mouse entre el
 * motor grafico y la interfaz.
 */

public class MouseListener {

    private static final double DOUBLE_CLICK_TIME = 0.2;
    private static final ImGuiIO io = ImGui.getIO();
    private static MouseListener instance;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private final boolean[] mouseButtonDobleClickPressed = new boolean[3];
    private final boolean[] mouseButtonReleased = new boolean[3];

    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean isDragging;
    private double lastTimeClick;

    /**
     * Constructor privado para nuestro Singleton.
     */
    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
        this.lastTimeClick = 0;
    }

    /**
     * @return Mismo objeto (Patron de diseño Singleton).
     */
    public static MouseListener get() {
        if (MouseListener.instance == null) MouseListener.instance = new MouseListener();
        return MouseListener.instance;
    }

    /**
     * @desc: Funcion callBack para detectar y actualizar la posicion del mouse.
     */
    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    /**
     * @desc: Funcion callBack para detectar los botones pulsados.
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        final boolean[] mouseDown = new boolean[5];

        mouseDown[0] = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
        mouseDown[1] = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
        mouseDown[2] = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
        mouseDown[3] = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
        mouseDown[4] = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;

        io.setMouseDown(mouseDown);

        if (!io.getWantCaptureMouse() && mouseDown[1]) ImGui.setWindowFocus(null);

        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
                get().isDragging = true;

                // Doble click!
                double currentTime = glfwGetTime();

                get().mouseButtonDobleClickPressed[button] = currentTime - get().lastTimeClick <= DOUBLE_CLICK_TIME;

                get().lastTimeClick = currentTime;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().mouseButtonReleased[button] = true;
                get().isDragging = false;
            }
        }
    }

    /**
     * @desc: Funcion callBack para detectar el desplazamiento del mouse de una posicion a otra.
     */
    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
        io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean pressing(int button) {
        return get().mouseButtonPressed[button];
    }

    /**
     * @param button Boton que querramos detectar
     * @return True si estamos apretamos un dicho boton, caso contrario false.
     */
    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) return get().mouseButtonPressed[button];
        return false;
    }

    /**
     * Detecta si hicimos 1 click
     */
    public static boolean mouseButtonClick(int button) {
        if (button < get().mouseButtonPressed.length) {
            boolean retVal = get().mouseButtonPressed[button];
            if (retVal) get().mouseButtonPressed[button] = false;
            return retVal;
        }
        return false;
    }

    /**
     * Detecta si hicimos doble click
     */
    public static boolean mouseButtonDoubleClick(int button) {
        if (button < get().mouseButtonPressed.length) {
            boolean retVal = get().mouseButtonDobleClickPressed[button];
            if (retVal) {
                get().lastTimeClick = 0;
                get().mouseButtonDobleClickPressed[button] = false;
            }
            return retVal;
        }
        return false;
    }

    /**
     * Detecta si se soltó un botón del mouse en este frame.
     */
    public static boolean mouseButtonReleased(int button) {
        if (button < get().mouseButtonReleased.length) {
            boolean retVal = get().mouseButtonReleased[button];
            get().mouseButtonReleased[button] = false; // consumir evento
            return retVal;
        }
        return false;
    }

    public static void resetReleasedButtons() {
        for (int i = 0; i < get().mouseButtonReleased.length; i++) {
            get().mouseButtonReleased[i] = false;
        }
    }
}

