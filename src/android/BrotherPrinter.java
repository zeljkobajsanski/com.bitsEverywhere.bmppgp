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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BrotherPrinter extends CordovaPlugin {

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
    //String filePath = "/storage/emulated/0/Bluetooth/20150116_144555.jpg";
    //PrinterStatus status = myPrinter.printPDF(filePath, 1);

    PrinterStatus status = null;
    try {
        Bitmap image = base64ToBitmap(data);
        status = myPrinter.printImage(image);
        callbackContext.success(status.errorCode.toString());
    } catch(Exception exc) {
        callbackContext.error(exc.getMessage());
    }
  }

  private Bitmap base64ToBitmap(String imageData)
    {
        byte[] imageAsBytes = Base64.decode(imageData.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
