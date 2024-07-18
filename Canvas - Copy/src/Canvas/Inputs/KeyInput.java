package Canvas.Inputs;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Canvas.Commands.Trigger;
import Canvas.Shapes.VisualJ;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

public class KeyInput implements KeyListener {
    @SuppressWarnings("unchecked")
    ArrayList<Object>[] pressedKeys = new ArrayList[2];
    private VisualJ vis;
    public KeyInput(VisualJ vis) {
        pressedKeys[0] = new ArrayList<Object>();
        pressedKeys[1] = new ArrayList<Object>();
        this.vis = vis;
        vis.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Invoked when a key is typed. Uses KeyChar, a character output
        // System.out.println("Key Typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Invoked when a physical key is pressed down. Uses KeyCode, an int
        // System.out.println("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
        for (int i  = 0; i < pressedKeys[0].size(); i++){
            if (KeyEvent.getKeyText(e.getKeyCode()).equals(pressedKeys[0].get(i)) || String.valueOf(e.getKeyChar()).equals(pressedKeys[0].get(i))){
                pressedKeys[1].set(i, true);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a physical key is released.
        // System.out.println("Key Released: " + KeyEvent.getKeyText(e.getKeyCode()));
        for (int i  = 0; i < pressedKeys[0].size(); i++){
            if (KeyEvent.getKeyText(e.getKeyCode()).equals(pressedKeys[0].get(i)) || String.valueOf(e.getKeyChar()).equals(pressedKeys[0].get(i))){
                pressedKeys[1].set(i, false);
                break;
            }
        }
    }

    private boolean checkKeyPressed(String key){
        return (boolean)pressedKeys[1].get(pressedKeys[0].indexOf(key));
    }

    public Trigger keyPressed(String key){
        if (pressedKeys[0].contains(key)){
            return new Trigger(()->checkKeyPressed(key));
        }
        pressedKeys[0].add(key);
        pressedKeys[1].add(false);
        return new Trigger(()->checkKeyPressed(key));
    }
    public BooleanSupplier isKeyPressed(String key){
        if (pressedKeys[0].contains(key)){
            return ()->checkKeyPressed(key);
        }
        pressedKeys[0].add(key);
        pressedKeys[1].add(false);
        return ()->checkKeyPressed(key);
    }

    public String systemInput(String enterMessage){
        Scanner scanner = new Scanner(System.in);
        System.out.print(enterMessage);
        String inputString = scanner.nextLine();
        scanner.close();
        return inputString;
    }
}