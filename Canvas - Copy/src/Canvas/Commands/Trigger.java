package Canvas.Commands;

import java.util.function.BooleanSupplier;

public class Trigger {
    BooleanSupplier supplier;
    public Trigger (BooleanSupplier supplier){
        this.supplier = supplier;
    }
    private class BoolClass{
        private boolean bool;
        private BoolClass(boolean init){
            bool = init;
        }
        private void set(boolean bool){
            this.bool = bool;
        }
        private boolean getBoolean(){
            return bool;
        }
    }
    public Trigger onTrue(Runnable runner){
        BoolClass wasOn = new BoolClass(false);
        Runnable checker = ()->{
            if (supplier.getAsBoolean()){
                if (!wasOn.getBoolean()){
                    runner.run();
                    wasOn.set(true);
                }
            }else{
                wasOn.set(false);
            }
        };
        Command command = new Command(checker, 10);
        command.start();
        return this;
    }
    public Trigger onTrue(CommandBase command){
        return onTrue(command.getRunnable());
    }
    public Trigger onFalse(Runnable runner){
        BoolClass wasOn = new BoolClass(true);
        Runnable checker = ()->{
            if (!supplier.getAsBoolean()){
                if (!wasOn.getBoolean()){
                    runner.run();
                    wasOn.set(true);
                }
            }else{
                wasOn.set(false);
            }
        };
        Command command = new Command(checker, 10);
        command.start();
        return this;
    }
    public Trigger onFalse(CommandBase command){
        return onFalse(command.getRunnable());
    }
    public Trigger whileTrue(Runnable runner, double time){
        Runnable checker = ()->{
            if (supplier.getAsBoolean()){
                runner.run();
            }
        };
        Command command = new Command(checker, time);
        command.start();
        return this;
    }
    public Trigger whileTrue(CommandBase command){
        return whileTrue(command.getRunnable(), command.getTimer());
    }
    public Trigger whileFalse(Runnable runner, double time){
        Runnable checker = ()->{
            if (!supplier.getAsBoolean()){
                runner.run();
            }
        };
        Command command = new Command(checker, time);
        command.start();
        return this;
    }
    public Trigger whileFalse(CommandBase command){
        return whileFalse(command.getRunnable(), command.getTimer());
    }
}
