/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goaltracker;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Kshitij Gundale
 */
class GoalTrack implements Serializable {

    LocalDate date;
    int num;
}

public class GoalInfo implements Serializable {

    int goal_id;
    String name;
    String type;
    String unit;
    LocalDate endDate;
    int target;

    ArrayList<GoalTrack> progress;

    GoalInfo() {
        this.unit = "units";
        this.target = 1;
        this.progress = new ArrayList<>();
    }
}
