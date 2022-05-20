package sample;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Zegar implements Runnable{

    private int godziny = 0;
    private int minuty = 0;
    private int sekundy = 0;
    private Text text;
    private Gra gra;

    public Zegar(Text text, Gra gra) {
        this.gra=gra;
        this.text = text;
        this.text.setFont(new Font(20));
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                text.setText("życia:"+(10-gra.getSkuchy())+"|czas:"+godziny+":"+minuty+":"+sekundy+"|punkty:"+gra.getPunkty());
                break;
            }
            if (sekundy<60)
                sekundy++;
            else{
                sekundy=0;
                if (minuty<60)
                    minuty++;
                else{
                    minuty=0;
                    godziny++;
                }
            }
            text.setText("życia:"+(10-gra.getSkuchy())+"|czas:"+godziny+":"+minuty+":"+sekundy+"|punkty:"+gra.getPunkty());
        }while (!Thread.currentThread().isInterrupted());
    }

    public int forScore(){
        return godziny*60*60+minuty*60+sekundy;
    }
}
