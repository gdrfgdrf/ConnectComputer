package cn.gdrfgdrf.ConnectComputerServer.Validation.Group;

/**
 * @author gdrfgdrf
 */
public interface ValidationGroup {
    interface IntegerNotNullId {}
    interface IntegerPositiveId {}

    interface StringNotBlankUsername {}
    interface StringMaxLengthUsername {}
    interface StringPatternUsername {}

    interface StringNotBlankPassword {}

    interface StringNotBlankOriginalPassword {}

    interface StringNotBlankDisplayName {}
    interface StringMaxLengthDisplayName {}
}
