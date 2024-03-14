package Canvas;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.util.function.Supplier;

import javax.swing.event.ChangeListener;

/**
 * Command class is a timer/thread mix
 */
public class Trigger {
    // ChangeListener listener = new ChangeListener(valueSupplier);
    public Trigger(Runnable toRun, Supplier<Object> toCheck){
        PropertyChangeSupport support = new PropertyChangeSupport(0);

        // Setting up a listener using PropertyChangeListener
        support.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                toRun.run();
            }
        });
        support.firePropertyChange("value", 10, 20);
    }
    
}
// import java.util.function.Supplier;

// public class Main {
//     public static void main(String[] args) {
//         // Example Supplier
//         Supplier<Integer> valueSupplier = () -> {
//             // Some logic to obtain the value dynamically
//             return 42; // Example value
//         };

//         // Setting up a listener using a Supplier
//         ChangeListener listener = new ChangeListener(valueSupplier);
//         listener.valueChanged(); // This will obtain the value from the Supplier and trigger the listener
//     }
// }

// class ChangeListener {
//     private Supplier<Integer> valueSupplier;

//     public ChangeListener(Supplier<Integer> valueSupplier) {
//         this.valueSupplier = valueSupplier;
//     }

//     public void valueChanged() {
//         int value = valueSupplier.get();
//         System.out.println("Value changed to: " + value);
//         // Do something when value changes
//     }
// }