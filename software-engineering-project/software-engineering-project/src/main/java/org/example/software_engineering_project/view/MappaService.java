package org.example.software_engineering_project.view;



import org.example.software_engineering_project.model.Position;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MappaService {

    private final HashMap<Position, String> mappa = new HashMap<>();

    public HashMap<Position, String> getMappa() {
        return mappa;
    }

    public void aggiungiLuogo(Position posizione, String nomeLuogo) {
        mappa.put(posizione, nomeLuogo);
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

