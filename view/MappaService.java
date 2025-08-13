package org.example.app.view;
import org.example.app.model.Position;
import java.util.HashMap;
import java.util.Map;


public class MappaService {
  private HashMap<Position,String>  mappa;

    public HashMap<Position, String> getMappa() {
        return mappa;
    }

    public void stampaMappa() {
        System.out.println("===== MAPPA LUOGHI =====");
        if (mappa.isEmpty()) {
            System.out.println("Nessun luogo presente nella mappa.");
            return;
        }
        for (Map.Entry<Position, String> entry : mappa.entrySet()) {
            Position pos = entry.getKey();
            String nomeLuogo = entry.getValue();
            System.out.println(
                    nomeLuogo + " -> Lat: " + pos.getLatitudine() +
                            ", Lon: " + pos.getLongitudine()
            );
        }
    }

}
