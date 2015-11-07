package com.bitseverywhere.brother;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.bluetooth.BluetoothAdapter;
import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;

public class Printer extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("print")) {
          String data = args.getString(0);
          this.print(data, callbackContext);
          return true;
      }
      return false;
  }

  private void print(String data, CallbackContext callbackContext) {
    Printer myPrinter = new Printer();
    PrinterInfo myPrinterInfo = new PrinterInfo();
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    myPrinter.setBluetooth(bluetoothAdapter);
    myPrinterInfo = myPrinter.getPrinterInfo();
    myPrinterInfo.printerModel = PrinterInfo.Model.PJ_662;
    myPrinterInfo.paperSize = PrinterInfo.PaperSize.A4;
    myPrinterInfo.orientation = PrinterInfo.Orientation.PORTRAIT;
    myPrinterInfo.numberOfCopies = 1;
    myPrinter.setPrinterInfo(myPrinterInfo);
    //String filePath = "/storage/emulated/0/Digital Editions/racun.pdf";
    String filePath = "/storage/emulated/0/Bluetooth/20150116_144555.jpg";
    //PrinterStatus status = myPrinter.printPDF(filePath, 1);
    PrinterStatus status = null;
    try {
        status = myPrinter.printFile(filePath);
        callbackContext.success();
    } catch(Exception exc) {
        callbackContext.error(exc.getMessage());ß
    }
  }
}
