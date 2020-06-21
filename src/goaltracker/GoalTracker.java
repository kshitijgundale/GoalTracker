/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goaltracker;


import java.io.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

/**
 *
 * @author Kshitij Gundale
 */
public class GoalTracker extends Application implements Serializable
{
    static int[] goals;
    static int num_of_goals;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 0, 20, 20));
        HBox h = new HBox();
        
        Button b = new Button("Go to Goals");
        Button a = new Button("Add goals");
        Button ab = new Button("About");
        b.setMaxWidth(Double.MAX_VALUE);
        ab.setMaxWidth(Double.MAX_VALUE);
        a.setMaxWidth(Double.MAX_VALUE);
        b.setFont(new Font("Arial",20));
        ab.setFont(new Font("Arial",20));
        a.setFont(new Font("Arial",20));
        b.setPrefWidth(150);
        a.setPrefWidth(150);
        ab.setPrefWidth(150);
        
        
        Text head = new Text("Goal Tracker");
        head.setFont(Font.font("verdanna", FontWeight.BOLD, FontPosture.ITALIC, 50));
        head.setStrokeWidth(80);
        head.setFill(Color.GAINSBORO);
        //FileInputStream inputstream = new FileInputStream("/src/goaltracker.png");
        Image im = new Image("file:goaltracker.png");
        ImageView image = new ImageView(im);
        h.getChildren().addAll(b,a,ab);
        h.setPadding(new Insets(0, 20, 10, 20)); 
        h.setSpacing(100);
        VBox v = new VBox();
        image.setFitHeight(300);
        image.setFitWidth(500);
        v.getChildren().addAll(head,image,h);
        h.setAlignment(Pos.CENTER);
        v.setAlignment(Pos.CENTER);
        v.setSpacing(50);
        pane.setCenter(v);
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene s = new Scene(pane,800,800);
        primaryStage.setScene(s);
        primaryStage.setTitle("GoalTracker-Home");
        primaryStage.show();
        
        // "About" Stage
        ab.setOnAction(e->{
            Stage aboutApp = new Stage();
            BorderPane root = new BorderPane();
            Text info = new Text("GoalTracker is software developed to provide a platform to track goals. It is developed as final project for OOCP 2020 course,PDPU,Gandhinagar.");
            info.setFont(new Font("Arial",20));
            info.setFill(Color.BLACK);
            root.setLeft(info);
            root.setMargin(info,new Insets(20,20,20,20));
            Scene about_scene = new Scene(root,800,800);
            aboutApp.setScene(about_scene);
            aboutApp.setTitle("About");
            aboutApp.show();
        });
  
        
        
        //Name of goal
        TextField name = new TextField();
        Label nameLabel = new Label("Enter the name of the goal:");
        nameLabel.setPrefWidth(400);
        HBox nameBox = new HBox();
        nameLabel.setFont(new Font("Arial",25));
        nameBox.getChildren().addAll(nameLabel,name);
        nameBox.setMargin(name,new Insets(20,20,20,5));
        nameBox.setMargin(nameLabel,new Insets(20,5,20,20));
        
        //Name Event Handler
        name.setOnMouseClicked(e->{
            name.setStyle("-fx-display-caret: true;");
        });
        name.setOnAction(e->{
            name.setStyle("-fx-display-caret: false;");
        });
        
        //Type of goal
        ComboBox<String> goalType = new ComboBox();
        goalType.getItems().addAll("DAILY TARGET","TOTAL TARGET");
        goalType.setValue("DAILY TARGET");
        Label goalTypeLabel = new Label("Type of Goal:");
        goalTypeLabel.setPrefWidth(400);
        HBox goalTypeBox = new HBox();
        goalTypeLabel.setFont(new Font("Arial",25));
        goalTypeBox.getChildren().addAll(goalTypeLabel,goalType);
        goalTypeBox.setMargin(goalType,new Insets(5,20,20,5));
        goalTypeBox.setMargin(goalTypeLabel,new Insets(5,5,20,20));
        
        
        //Unit of Measurement
        TextField unit = new TextField();
        Label unitLabel = new Label("Unit of measurement (e.g. hours):");
        unitLabel.setPrefWidth(400);
        HBox unitBox = new HBox();
        unitLabel.setFont(new Font("Arial",25));
        unitBox.getChildren().addAll(unitLabel,unit);
        unitBox.setMargin(unit,new Insets(5,20,20,5));
        unitBox.setMargin(unitLabel,new Insets(5,5,20,20));
        
        //Unit Event Handler
        unit.setOnMouseClicked(e->{
            unit.setStyle("-fx-display-caret: true;");
        });
        unit.setOnAction(e->{
            unit.setStyle("-fx-display-caret: false;");
        });
        
        //End Date of Goal using DatePicker
        DatePicker dp = new DatePicker();
        Label dpLabel = new Label("End Date:");
        dpLabel.setPrefWidth(400);
        HBox dpBox = new HBox();
        dpLabel.setFont(new Font("Arial",25));
        dpBox.getChildren().addAll(dpLabel,dp);
        dpBox.setMargin(dp,new Insets(5,20,20,5));
        dpBox.setMargin(dpLabel,new Insets(5,5,20,20));
        
        dp.setDayCellFactory(picker -> new DateCell() {
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();

            setDisable(empty || date.compareTo(today) < 0 );
          }
        });
        
        //Target Number of times 
        TextField target = new TextField();
        Label targetLabel = new Label("Target Number:");
        targetLabel.setPrefWidth(400);
        HBox targetBox = new HBox();
        targetLabel.setFont(new Font("Arial",25));
        targetBox.getChildren().addAll(targetLabel,target);
        targetBox.setMargin(target,new Insets(5,20,20,5));
        targetBox.setMargin(targetLabel,new Insets(5,5,20,20));
        
        //Target Event Handler
        target.setOnMouseClicked(e->{
            target.setStyle("-fx-display-caret: true;");
        });
        target.setOnAction(e->{
            target.setStyle("-fx-display-caret: false;");
        });
        
        //Apply Button
        HBox op = new HBox();
        Button addGoalApply = new Button("Create");
        addGoalApply.setFont(new Font("Arial",25));
        op.setMargin(addGoalApply,new Insets(30,30,30,30));
        op.getChildren().addAll(addGoalApply);
        op.setAlignment(Pos.BOTTOM_RIGHT);
        
        addGoalApply.setOnAction(e->{
            num_of_goals++;
            GoalInfo g = new GoalInfo();
            g.name = name.getText();
            g.type = goalType.getValue();
            g.unit = unit.getText();
            g.endDate = dp.getValue();
            g.target = Integer.parseInt(target.getText());
            try
            {
                FileOutputStream file = new FileOutputStream("Goal1.txt");
                ObjectOutputStream ob = new ObjectOutputStream(file);
                ob.writeObject(g);
                ob.close();
                file.close();
            }
            catch(IOException ex)
            {
                System.out.println("Error");
            }
            b.fire();
        });
        
        //OK button
        Button addGoalOk  = new Button("OK");
        addGoalOk.setFont(new Font("Arial",25));
        op.setMargin(addGoalOk,new Insets(30,30,30,30));
        op.getChildren().add(addGoalOk);
        op.setAlignment(Pos.BOTTOM_RIGHT);
        
        addGoalOk.setOnAction(e->{
            if(name.getText().isEmpty() || dp.getValue()==null || target.getText().isEmpty() || !target.getText().matches("\\d+"))
            {
                addGoalApply.setDisable(true);
            }
            else
            {
                addGoalApply.setDisable(false);
                name.setDisable(true);
                target.setDisable(true);
            }
        });
        
        
        //Cancel Buttom
        Button addGoalCancel = new Button("Cancel");
        addGoalCancel.setFont(new Font("Arial",25));
        op.setMargin(addGoalCancel,new Insets(30,30,30,30));
        op.getChildren().add(addGoalCancel);
        op.setAlignment(Pos.BOTTOM_RIGHT);
        
        addGoalCancel.setOnAction(e->{
            name.setText("");
            unit.setText("");
            target.setText("");
            dp.setValue(null);
            primaryStage.setScene(s);
            primaryStage.setTitle("GoalTracker-Home");
        });
        
        if(name.getText().isEmpty() || dp.getValue()==null || target.getText().isEmpty() || !target.getText().matches("\\d+"))
        {
            addGoalApply.setDisable(true);
        }
        
        //Create "Add Goal" Scene
        BorderPane addGoalPane = new BorderPane();
        VBox addGoalBox = new VBox();
        addGoalBox.getChildren().addAll(nameBox,goalTypeBox,unitBox,dpBox,targetBox);
        addGoalPane.setLeft(addGoalBox);
        addGoalPane.setBottom(op);
        Scene addGoals = new Scene(addGoalPane,800,800);
        
        //Set Add goal Scene
        a.setOnAction(e->{
            primaryStage.setScene(addGoals);
            primaryStage.setTitle("Add Goal");
        });
        
        /*
            UI for showing goal.
         */
        //Creating container pane as HBox
        HBox conBox = new HBox();
        VBox rootGoalPane = new VBox();
        HBox topBox = new HBox();
        VBox leftOfTopBox = new VBox();
        VBox rightOfTopBox = new VBox();
        HBox middleBox = new HBox();
        VBox barBox = new VBox();

        //Nodes in topBox
        //Nodes in leftOfTopBox
        Text nameGoal = new Text("default");
        Text targetGoal = new Text("default");
        Text typeGoal = new Text("defualt");
        Text endDateGoal = new Text("default");
        nameGoal.setFont(new Font("Arial",25));
        targetGoal.setFont(new Font("Arial",25));
        typeGoal.setFont(new Font("Arial",25));
        endDateGoal.setFont(new Font("Arial",25));
        leftOfTopBox.getChildren().addAll(nameGoal,targetGoal,typeGoal,endDateGoal);
        leftOfTopBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        leftOfTopBox.setStyle("-fx-border-color: black;");
        leftOfTopBox.setMargin(nameGoal, new Insets(20,20,20,20));
        leftOfTopBox.setMargin(targetGoal, new Insets(20,20,20,20));
        leftOfTopBox.setMargin(typeGoal, new Insets(20,20,20,20));
        leftOfTopBox.setMargin(endDateGoal, new Insets(20,20,20,20));
        
        

        //Nodes in rightOFTopBox
        Text completed = new Text("Default");
        Text remaining = new Text("Default");
        Text status = new Text("Default");
        ProgressBar pb = new ProgressBar();
        pb.setProgress(0.5);
        completed.setFont(new Font("Arial",70));
        remaining.setFont(new Font("Arial",30));
        status.setFont(new Font("Arial",30));
        rightOfTopBox.getChildren().addAll(completed,remaining,status,pb);
        rightOfTopBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        rightOfTopBox.setStyle("-fx-border-color: black;");
        rightOfTopBox.setMargin(completed,new Insets(20,20,20,20));
        rightOfTopBox.setMargin(remaining,new Insets(20,20,20,20));
        rightOfTopBox.setMargin(status,new Insets(20,20,20,20));
        rightOfTopBox.setMargin(pb, new Insets(20,20,20,20));
        
        topBox.getChildren().addAll(leftOfTopBox,rightOfTopBox);
        topBox.setSpacing(50);
        

        //Nodes in middleBox
        DatePicker datepicker = new DatePicker();
        Text datepickerLabel = new Text("History");
        datepickerLabel.setFill(Color.WHITE);
        Text history = new Text("");
        history.setFill(Color.WHITE);
        datepickerLabel.setFont(new Font("Arial",25));
        history.setFont(new Font("Arial",25));
        middleBox.getChildren().addAll(datepickerLabel,datepicker,history);
        middleBox.setStyle("-fx-border-color: black;");
        middleBox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        middleBox.setMargin(datepickerLabel,new Insets(20,20,20,20));
        middleBox.setMargin(history,new Insets(20,20,20,20));
        middleBox.setMargin(datepicker,new Insets(20,20,20,20));
        

        //Barchart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Progress");
        BarChart barChart = new BarChart(xAxis, yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
        barChart.getData().add(dataSeries1);
        barBox.setMargin(barChart,new Insets(20,20,20,20));
        
        VBox infoBox = new VBox();
        Text maxText = new Text();
        Text minText = new Text();
        maxText.setFont(new Font("Arial",25));
        minText.setFont(new Font("Arial",25));
        infoBox.getChildren().addAll(maxText,minText);
        infoBox.setMargin(maxText,new Insets(20,20,20,20));
        infoBox.setMargin(minText,new Insets(20,20,20,20));
        infoBox.setAlignment(Pos.CENTER);
        
        barBox.getChildren().addAll(barChart,infoBox);
        
        //PieChart
        VBox pieBox = new VBox();
        PieChart pieChart = new PieChart();
        pieBox.getChildren().add(pieChart);
        
        
        
     
        HBox homeBox = new HBox();
        Button homeGoal = new Button("Home");
        Button update = new Button("Add Progress");
        homeGoal.setFont(new Font("Arial",25));
        update.setFont(new Font("Arial",25));
        homeBox.getChildren().addAll(update,homeGoal);
        homeBox.setMargin(homeGoal,new Insets(20,20,20,20));
        leftOfTopBox.setMargin(update, new Insets(20,20,20,20));
        homeBox.setAlignment(Pos.BASELINE_RIGHT);
        
        rootGoalPane.getChildren().addAll(topBox,middleBox,homeBox);
        rootGoalPane.setSpacing(10);
        conBox.getChildren().addAll(rootGoalPane,barBox,pieBox);
        conBox.setSpacing(100);
        conBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene goalScene = new Scene(conBox,800,800);
        
        
        homeGoal.setOnAction(e->{
            primaryStage.setScene(s);
            primaryStage.setTitle("GoalTracker-Home");
        });
        
        b.setOnAction(e->{
            GoalInfo gi = new GoalInfo();
            try
            {
                FileInputStream fi = new FileInputStream("Goal1.txt");
                ObjectInputStream ob = new ObjectInputStream(fi);
                gi = (GoalInfo)ob.readObject();
                ob.close();
                fi.close();
            }
            catch(IOException ex)
            {
                Stage errorb = new Stage();
                BorderPane errorPaneb = new BorderPane();
                Text errorTextb = new Text("No Goal added yet");
                errorPaneb.setCenter(errorTextb);
                Scene errorSceneb = new Scene(errorPaneb,200,200);
                errorb.setScene(errorSceneb);
                errorb.setTitle("Error");
                errorb.show();
            }
            catch(ClassNotFoundException ex)
            {
                System.exit(1);
            }
            if(gi.name!=null)
            {
                nameGoal.setText(gi.name);
                targetGoal.setText("Target: " + gi.target + " " + gi.unit);
                typeGoal.setText(gi.type);
                endDateGoal.setText(gi.endDate.toString());
                if(gi.progress.isEmpty())
                {
                    completed.setText(0+"");
                    remaining.setText(gi.target+" left");
                    status.setText("Ongoing");
                    pb.setProgress(0);
                    GoalTrack gt = new GoalTrack();
                    gt.date = LocalDate.now();
                    gt.num = 0;
                    gi.progress.add(gt);
                }
                else
                {
                    long i = DAYS.between(gi.progress.get(gi.progress.size()-1).date,LocalDate.now());
                    if(i!=0)
                    {
                        for(long j=i-1;j<i;j++)
                        {
                            GoalTrack tg = new GoalTrack();
                            LocalDate date = LocalDate.now();
                            tg.num = 0;
                            tg.date = date.minusDays(j);
                            gi.progress.add(tg);
                        }
                    }

                    if(gi.type.equals("DAILY TARGET"))
                    {
                        completed.setText(gi.progress.get(gi.progress.size()-1).num+"");
                        if((gi.target-gi.progress.get(gi.progress.size()-1).num)>=0)
                            remaining.setText((gi.target-gi.progress.get(gi.progress.size()-1).num)+" left");
                        else
                            remaining.setText(0 + " left");
                        if(gi.target>gi.progress.get(gi.progress.size()-1).num)
                        {
                            status.setText("Ongoing"); 
                        }
                        else
                        {
                            status.setText("Accomplished");
                        }
                        double f = (double) gi.progress.get(gi.progress.size()-1).num/(double) gi.target;
                        pb.setProgress(f);


                        int sf,nsf;
                        sf=nsf=0;
                        for(int g=0;g<gi.progress.size();g++)
                        {
                            if(gi.progress.get(g).num >= gi.target)
                                sf++;
                            else
                                nsf++;
                        }
                        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Successful Days",sf),new PieChart.Data("Unsuccessful Days",nsf));
                        pieChart.setData(pieChartData);

                    }
                    else if(gi.type.equals("TOTAL TARGET"))
                    {
                        int sum = 0;
                        int max = Integer.MIN_VALUE;
                        LocalDate maxDate = null;
                        LocalDate minDate = null;
                        int min = Integer.MAX_VALUE;
                        for(int o=0;o<gi.progress.size();o++)
                        {
                            sum += gi.progress.get(o).num;
                            if(gi.progress.get(o).num > max)
                            {
                                max = gi.progress.get(o).num;
                                maxDate = gi.progress.get(o).date;
                            }
                            if(gi.progress.get(o).num < min)
                            {
                                min = gi.progress.get(o).num;
                                minDate = gi.progress.get(o).date;
                            }
                        }
                        if(sum<gi.target)
                            status.setText("Ongoing");
                        else
                            status.setText("Accomplished");
                        completed.setText(sum+"");
                        if((gi.target-sum)>=0)
                            remaining.setText((gi.target-sum)+" left");
                        else
                            remaining.setText(0+" left");
                        double f = (double) sum/(double) gi.target;
                        pb.setProgress(f);
                        if(maxDate!=null && minDate!=null)
                        {
                            maxText.setText("Maximumm " + gi.unit + " in a day: " + max + " on " + maxDate.toString());
                            minText.setText("Minimum " + gi.unit + " in a day: " + min + " on " + minDate.toString());
                        }
                    }   

                }

                for(int g=0;g<gi.progress.size();g++)
                {
                    dataSeries1.getData().add(new XYChart.Data(gi.progress.get(g).date.toString(), gi.progress.get(g).num));
                }
                barChart.setStyle("-fx-bar-fill: blue");


                try
                {
                    FileOutputStream file = new FileOutputStream("Goal1.txt");
                    ObjectOutputStream obt = new ObjectOutputStream(file);
                    obt.writeObject(gi);
                    obt.close();
                    file.close();
                }
                catch(IOException eg)
                {
                    System.exit(1);
                }

                primaryStage.setScene(goalScene);
                primaryStage.setTitle("DashBoard");
                primaryStage.show();
            }
        });
        
        datepicker.setOnAction(e->{
            GoalInfo gi = new GoalInfo();
            try
            {
                FileInputStream fi = new FileInputStream("Goal1.txt");
                ObjectInputStream ob = new ObjectInputStream(fi);
                gi = (GoalInfo)ob.readObject();
                ob.close();
                fi.close();
            }
            catch(IOException ex)
            {
                System.exit(1);
            }
            catch(ClassNotFoundException ex)
            {
                System.exit(1);
            }
            boolean flag = false;
            for(int y=0;y<gi.progress.size();y++)
            {
                if(gi.progress.get(y).date.equals(datepicker.getValue()))
                {
                    flag = true;
                    history.setText(gi.progress.get(y).num + " " + gi.unit);
                }
            }
            if(!flag)
            {
                history.setText("No record found");
            }
        
        });
        
        
       
        VBox rootUpdate = new VBox();
        HBox updateBox = new HBox();
        Button updateOK = new Button("OK");
        Label progressLabel = new Label();
        progressLabel.setFont(new Font("Arial", 25));
        updateOK.setFont(new Font("Arial", 25));
        updateOK.setAlignment(Pos.BASELINE_RIGHT);
        TextField progressField = new TextField();
        updateBox.getChildren().addAll(progressLabel, progressField);
        updateBox.setMargin(progressLabel, new Insets(20, 20, 20, 20));
        updateBox.setMargin(progressField, new Insets(20, 20, 20, 20));
        rootUpdate.getChildren().addAll(updateBox, updateOK);

        Scene updateScene = new Scene(rootUpdate);
        Stage updateStage = new Stage();
        updateStage.setScene(updateScene);
        updateStage.setTitle("Add Progress");

        update.setOnAction(e -> {

            updateStage.show();

        });

        updateOK.setOnAction(ep -> {
            
            GoalInfo ig = new GoalInfo();
            try {
                FileInputStream fi = new FileInputStream("Goal1.txt");
                ObjectInputStream ob = new ObjectInputStream(fi);
                ig = (GoalInfo) ob.readObject();
                ob.close();
                fi.close();
            } catch (IOException ex) {
                System.exit(1);
            } catch (ClassNotFoundException ex) {
                System.exit(1);
            }
            progressLabel.setText(ig.unit + " to add");
            if (!progressField.getText().isEmpty() || progressField.getText().matches("\\d+")) 
            {
                ig.progress.get(ig.progress.size() - 1).num += Integer.parseInt(progressField.getText());
                try
                {
                    FileOutputStream file = new FileOutputStream("Goal1.txt");
                    ObjectOutputStream ob = new ObjectOutputStream(file);
                    ob.writeObject(ig);
                    ob.close();
                    file.close();
                }
                catch(IOException ex)
                {
                    System.exit(1);
                }
                
                updateStage.close();
                b.fire();
                datepicker.fireEvent(ep);
                
            }
        });
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
