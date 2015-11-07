var Printer = function() {}
Printer.prototype.print = function(data, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "BrotherPrinter", "print", [data]);
};

module.exports = new Printer();
