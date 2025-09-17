package com.myorg.unempx.ui;

import com.myorg.unempx.model.UnemploymentRecord;
import com.myorg.unempx.service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnempXApp extends JFrame {
    private DataProcessor processor;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private UnemploymentChartPanel chartPanel;
    private JComboBox<String> analysisCombo;

    public UnempXApp() {
        setTitle("UnempX - Unemployment Analysis");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        loadData();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        String[] analyses = {"Annual Rate", "Min/Max", "Moving Average (12mo)", "Top 3 Years"};
        analysisCombo = new JComboBox<>(analyses);

        // Table setup
        String[] columns = {"Year","Jan","Feb","Mar","Apr","May","Jun",
                "Jul","Aug","Sep","Oct","Nov","Dec","Avg"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col){ return false; }
        };
        dataTable = new JTable(tableModel);
        dataTable.setAutoCreateRowSorter(true);

        // Context menu
        JPopupMenu popup = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        popup.add(editItem); popup.add(deleteItem);
        dataTable.setComponentPopupMenu(popup);

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(analysisCombo);
        JButton addButton = new JButton("Add New");
        addButton.addActionListener(e -> new InputForm().setVisible(true));
        topPanel.add(addButton);

        // Chart panel
        chartPanel = new UnemploymentChartPanel();
        JButton toggleChart = new JButton("Toggle Chart");
        toggleChart.addActionListener(e -> {
            chartPanel.setVisible(!chartPanel.isVisible());
            revalidate(); repaint();
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(dataTable),
                createChartContainer(toggleChart));
        split.setDividerLocation(400);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(split, BorderLayout.CENTER);
        add(mainPanel);

        // Event handlers
        analysisCombo.addActionListener(e -> updateAnalysis());
        editItem.addActionListener(e -> editRecord());
        deleteItem.addActionListener(e -> deleteRecord());
    }

    private JPanel createChartContainer(JButton toggleBtn) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(chartPanel), BorderLayout.CENTER);
        p.add(toggleBtn, BorderLayout.SOUTH);
        return p;
    }

    private void loadData() {
        try {
            List<UnemploymentRecord> list = new CsvReader().readData();
            processor = new DataProcessor(list);
            updateTable();
            updateAnalysis();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (UnemploymentRecord r : processor.getRecords()) {
            Object[] row = new Object[14];
            row[0] = r.getYear();
            List<Double> rates = r.getMonthlyRates();
            for (int i = 0; i < 12; i++) {
                row[i+1] = String.format("%.1f", rates.get(i));
            }
            row[13] = String.format("%.2f", r.getAnnualAverage());
            tableModel.addRow(row);
        }
    }

    private void updateAnalysis() {
        String sel = (String) analysisCombo.getSelectedItem();
        updateTable();
        chartPanel.updateChart(sel, processor);
    }

    private void editRecord() {
        int row = dataTable.getSelectedRow();
        if (row < 0) return;
        int modelRow = dataTable.convertRowIndexToModel(row);
        UnemploymentRecord rec = processor.getRecords().get(modelRow);
        new InputForm(rec, modelRow).setVisible(true);
    }

    private void deleteRecord() {
        int row = dataTable.getSelectedRow();
        if (row < 0) return;
        int modelRow = dataTable.convertRowIndexToModel(row);
        processor.getRecords().remove(modelRow);
        saveData();
        updateTable();
        updateAnalysis();
    }

    private void saveData() {
        try {
            new CsvWriter().writeData(processor.getRecords());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving data: " + ex.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class for Add/Edit form
    private class InputForm extends JDialog {
        private final JTextField txtYear = new JTextField(5);
        private final JTextField[] monthFields = new JTextField[12];
        private final Integer editIndex;

        public InputForm() {
            this(null, null);
        }
        public InputForm(UnemploymentRecord record, Integer index) {
            super(UnempXApp.this, index == null ? "Add New Record" : "Edit Record", true);
            this.editIndex = index;
            initForm();
            if (record != null) loadRecord(record);
        }

        private void initForm() {
            setLayout(new BorderLayout());
            JPanel p = new JPanel(new GridLayout(0,2,5,5));
            p.add(new JLabel("Year:")); p.add(txtYear);
            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            for (int i = 0; i < 12; i++) {
                p.add(new JLabel(months[i] + ":"));
                monthFields[i] = new JTextField(5);
                p.add(monthFields[i]);
            }
            JButton btnSave = new JButton("Save");
            btnSave.addActionListener(this::saveRecord);
            add(p, BorderLayout.CENTER);
            add(btnSave, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(UnempXApp.this);
        }

        private void loadRecord(UnemploymentRecord r) {
            txtYear.setText(String.valueOf(r.getYear()));
            List<Double> rates = r.getMonthlyRates();
            for (int i = 0; i < 12; i++) {
                monthFields[i].setText(String.format("%.1f", rates.get(i)));
            }
        }

        private void saveRecord(ActionEvent e) {
            try {
                int year = Integer.parseInt(txtYear.getText());
                if (year <= 0) throw new NumberFormatException("Year must be positive");
                List<Double> rates = new ArrayList<>();
                for (JTextField f : monthFields) {
                    String txt = f.getText().replace(",", ".");
                    double v = Double.parseDouble(txt);
                    if (v < 0.0 || v > 100.0) throw new NumberFormatException("Rate must be between 0 and 100");
                    rates.add(v);
                }
                if (editIndex != null) {
                    processor.getRecords().set(editIndex, new UnemploymentRecord(year, rates));
                } else {
                    processor.getRecords().add(new UnemploymentRecord(year, rates));
                }
                saveData();
                updateTable(); updateAnalysis();
                JOptionPane.showMessageDialog(this, "Record saved successfully!");
                dispose();
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input: " + ex2.getMessage(),
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ======= NUEVO MÉTODO DE DEMO CONSOLA =======
    public static void mainConsoleDemo(String[] args) {
        System.out.println("== UnempX Console Demo ==");
        if (args.length < 1) {
            System.out.println("Please provide the path to the CSV file as argument.");
            return;
        }
        String csvFilePath = args[0];

        try {
            List<UnemploymentRecord> data = new CsvReader().readData();
            DataProcessor service = new DataProcessor(data);
            double average = data.stream()
                    .mapToDouble(UnemploymentRecord::getAnnualAverage)
                    .average()
                    .orElse(0.0);
            System.out.printf("Average unemployment (annual): %.2f\n", average);

            var minmax = service.getMinMax();
            System.out.printf("Min value: %.2f in year %d\n",
                    minmax.get("minValue"), minmax.get("minYear"));
            System.out.printf("Max value: %.2f in year %d\n",
                    minmax.get("maxValue"), minmax.get("maxYear"));

            Scanner sc = new Scanner(System.in);
            System.out.print("¿Deseas agregar un año? (y/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("Año: ");
                int year = Integer.parseInt(sc.nextLine());
                List<Double> rates = new ArrayList<>();
                String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                for (String m : months) {
                    System.out.print(m + ": ");
                    rates.add(Double.parseDouble(sc.nextLine().replace(",", ".")));
                }
                data.add(new UnemploymentRecord(year, rates));
                new CsvWriter().writeData(data);
                System.out.println("¡Guardado!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ======= AJUSTE EN MAIN =======
    public static void main(String[] args) {
        // Si el primer argumento es "--console", ejecuta demo consola
        if (args.length > 0 && args[0].equals("--console")) {
            mainConsoleDemo(args.length > 1 ? new String[]{args[1]} : new String[0]);
        } else {
            SwingUtilities.invokeLater(() -> new UnempXApp().setVisible(true));
        }
    }
}