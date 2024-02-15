package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Processor;

/**
 * @author gdrfgdrf
 */
public interface ExceptionPostProcessor<T extends Throwable> {
    Exception postProcessorBeforeHandler(T e);
}
