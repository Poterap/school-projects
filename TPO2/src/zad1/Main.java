/**
 *
 *  @author Potera Patryk S22683
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    View view = new View();
    Controler controler = new Controler(view);

  }
}
