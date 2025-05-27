package View;

import Model.Store;
import Model.StoreDAO;
import Model.Brand;
import Model.BrandDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.border.TitledBorder;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JPanel welcomePanel;
    private JPanel adminPanel;
    private CardLayout cardLayout;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton selectedButton = null;
    private JTable storeTable;
    private JTable brandTable;
    private StoreDAO storeDAO;
    private BrandDAO brandDAO;
    private final Color DEFAULT_COLOR = new Color(50, 50, 50);
    private final Color SELECTED_COLOR = new Color(70, 130, 180);
    
    public MainView() {
        initComponents();
    }
    
    private void initComponents() {
        storeDAO = new StoreDAO();
        brandDAO = new BrandDAO();
        setTitle("Cửa hàng xe đạp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createWelcomePanel();
        createAdminPanel();
        
        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(adminPanel, "admin");
        
        add(mainPanel);
    }

    private void createAdminPanel() {
        adminPanel = new JPanel(new BorderLayout());
        createLeftPanel();
        createRightPanel();
        adminPanel.add(leftPanel, BorderLayout.WEST);
        adminPanel.add(rightPanel, BorderLayout.CENTER);
        refreshStoreTable();
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBackground(DEFAULT_COLOR);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Nút quay về
        JButton backButton = createBackButton();
        leftPanel.add(backButton);

        // Menu items
        String[] menuItems = {"Quản lý cửa hàng", "Quản lý nhãn hàng", "Quản lý loại hàng", 
                            "Quản lý sản phẩm", "Quản lý số lượng", "Quản lý khách hàng", 
                            "Quản lý đơn hàng", "Quản lý nhân viên"};

        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createMenuButton(menuItems[i]);
            leftPanel.add(menuButton);
            
            // Set mặc định cho nút đầu tiên (Quản lý cửa hàng)
            if (i == 0) {
                menuButton.setBackground(SELECTED_COLOR);
                selectedButton = menuButton;
            }
        }
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Quay về trang chính");
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "welcome");
            if (selectedButton != null) selectedButton.setBackground(DEFAULT_COLOR);
        });
        return backButton;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(DEFAULT_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button != selectedButton) button.setBackground(new Color(70, 70, 70));
            }
            public void mouseExited(MouseEvent e) {
                if (button != selectedButton) button.setBackground(DEFAULT_COLOR);
            }
        });

        button.addActionListener(e -> {
            if (selectedButton != null) selectedButton.setBackground(DEFAULT_COLOR);
            button.setBackground(SELECTED_COLOR);
            selectedButton = button;
            handleMenuItemClick(text);
        });

        return button;
    }

    private void handleMenuItemClick(String menuItem) {
        switch (menuItem) {
            case "Quản lý cửa hàng" -> {
                showStorePanel();
                refreshStoreTable();
            }
            case "Quản lý nhãn hàng" -> {
                showBrandPanel();
                refreshBrandTable();
            }
        }
    }

    private void createRightPanel() {
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // Panel danh sách
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setPreferredSize(new Dimension(0, 400));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách cửa hàng"));

        // Tạo bảng
        String[] columnNames = {"ID", "Tên cửa hàng", "Số điện thoại", "Email", 
                              "Địa chỉ", "Thành phố", "Tỉnh/TP", "Mã bưu chính"};
        storeTable = new JTable(new DefaultTableModel(columnNames, 0));
        listPanel.add(new JScrollPane(storeTable), BorderLayout.CENTER);

        // Panel chức năng
        JPanel functionPanel = createFunctionPanel();

        rightPanel.add(listPanel, BorderLayout.CENTER);
        rightPanel.add(functionPanel, BorderLayout.SOUTH);
    }

    private JPanel createFunctionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        String[] buttonNames = {"Thêm", "Sửa", "Xóa", "Làm mới"};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            panel.add(button);
            
            button.addActionListener(e -> {
                if (selectedButton.getText().equals("Quản lý cửa hàng")) {
                    handleStoreFunction(name);
                } else if (selectedButton.getText().equals("Quản lý nhãn hàng")) {
                    handleBrandFunction(name);
                }
            });
        }
        return panel;
    }
    
    private void createWelcomePanel() {
        welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(240, 240, 240));
        
        JLabel welcomeLabel = new JLabel("Chào mừng đến với Cửa hàng xe đạp");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        
        JButton guestButton = createStyledButton("Guest");
        JButton memberButton = createStyledButton("Member");
        JButton adminButton = createStyledButton("Admin");
        
        buttonPanel.add(guestButton);
        buttonPanel.add(memberButton);
        buttonPanel.add(adminButton);
        
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(buttonPanel);
        
        guestButton.addActionListener(e -> {
            // TODO: Chuyển đến guest view
        });
        
        memberButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển");
        });
        
        adminButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "admin");
            if (selectedButton != null) {
                selectedButton.setBackground(DEFAULT_COLOR);
            }
            selectedButton = null;
        });
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    private void showAddStoreDialog() {
        JDialog dialog = new JDialog(this, "Thêm cửa hàng mới", true);
        dialog.setLayout(new GridLayout(9, 2, 5, 5));
        
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField streetField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField stateField = new JTextField();
        JTextField zipField = new JTextField();
        
        dialog.add(new JLabel("Tên cửa hàng:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Số điện thoại:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Địa chỉ:"));
        dialog.add(streetField);
        dialog.add(new JLabel("Thành phố:"));
        dialog.add(cityField);
        dialog.add(new JLabel("Tỉnh/TP:"));
        dialog.add(stateField);
        dialog.add(new JLabel("Mã bưu chính:"));
        dialog.add(zipField);
        
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        
        saveButton.addActionListener(e -> {
            Store store = new Store();
            store.setStoreName(nameField.getText());
            store.setPhone(phoneField.getText());
            store.setEmail(emailField.getText());
            store.setStreet(streetField.getText());
            store.setCity(cityField.getText());
            store.setState(stateField.getText());
            store.setZipCode(zipField.getText());
            
            if (storeDAO.insertStore(store)) {
                refreshStoreTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Thêm cửa hàng mới thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm cửa hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showEditStoreDialog(int selectedRow) {
        int storeId = (int) storeTable.getValueAt(selectedRow, 0);
        Store store = storeDAO.getStoreById(storeId);
        
        if (store == null) {
            JOptionPane.showMessageDialog(this, "Không thể tìm thấy thông tin cửa hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa thông tin cửa hàng", true);
        dialog.setLayout(new GridLayout(9, 2, 5, 5));
        
        JTextField nameField = new JTextField(store.getStoreName());
        JTextField phoneField = new JTextField(store.getPhone());
        JTextField emailField = new JTextField(store.getEmail());
        JTextField streetField = new JTextField(store.getStreet());
        JTextField cityField = new JTextField(store.getCity());
        JTextField stateField = new JTextField(store.getState());
        JTextField zipField = new JTextField(store.getZipCode());
        
        dialog.add(new JLabel("Tên cửa hàng:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Số điện thoại:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Địa chỉ:"));
        dialog.add(streetField);
        dialog.add(new JLabel("Thành phố:"));
        dialog.add(cityField);
        dialog.add(new JLabel("Tỉnh/TP:"));
        dialog.add(stateField);
        dialog.add(new JLabel("Mã bưu chính:"));
        dialog.add(zipField);
        
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        
        saveButton.addActionListener(e -> {
            store.setStoreName(nameField.getText());
            store.setPhone(phoneField.getText());
            store.setEmail(emailField.getText());
            store.setStreet(streetField.getText());
            store.setCity(cityField.getText());
            store.setState(stateField.getText());
            store.setZipCode(zipField.getText());
            
            if (storeDAO.updateStore(store)) {
                refreshStoreTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void deleteStore(int selectedRow) {
        int storeId = (int) storeTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa cửa hàng này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (storeDAO.deleteStore(storeId)) {
                refreshStoreTable();
                JOptionPane.showMessageDialog(this, "Xóa cửa hàng thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa cửa hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshStoreTable() {
        List<Store> stores = storeDAO.getAllStores();
        DefaultTableModel model = (DefaultTableModel) storeTable.getModel();
        model.setRowCount(0);
        
        for (Store store : stores) {
            model.addRow(new Object[]{
                store.getStoreId(),
                store.getStoreName(),
                store.getPhone(),
                store.getEmail(),
                store.getStreet(),
                store.getCity(),
                store.getState(),
                store.getZipCode()
            });
        }
    }

    private void showBrandPanel() {
        ((TitledBorder) ((JPanel) rightPanel.getComponent(0)).getBorder())
            .setTitle("Danh sách nhãn hàng");
        
        String[] columnNames = {"ID", "Tên nhãn hàng"};
        brandTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane scrollPane = new JScrollPane(brandTable);
        
        JPanel listPanel = (JPanel) rightPanel.getComponent(0);
        listPanel.removeAll();
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.revalidate();
        listPanel.repaint();
    }

    private void refreshBrandTable() {
        List<Brand> brands = brandDAO.getAllBrands();
        DefaultTableModel model = (DefaultTableModel) brandTable.getModel();
        model.setRowCount(0);
        
        for (Brand brand : brands) {
            model.addRow(new Object[]{
                brand.getBrandId(),
                brand.getBrandName()
            });
        }
    }

    private void showAddBrandDialog() {
        JDialog dialog = new JDialog(this, "Thêm nhãn hàng mới", true);
        dialog.setLayout(new GridLayout(2, 2, 5, 5));
        
        JTextField nameField = new JTextField();
        
        dialog.add(new JLabel("Tên nhãn hàng:"));
        dialog.add(nameField);
        
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        
        saveButton.addActionListener(e -> {
            Brand brand = new Brand();
            brand.setBrandName(nameField.getText().trim());
            
            if (brandDAO.insertBrand(brand)) {
                refreshBrandTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Thêm nhãn hàng mới thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhãn hàng thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showEditBrandDialog(int selectedRow) {
        int brandId = (int) brandTable.getValueAt(selectedRow, 0);
        Brand brand = brandDAO.getBrandById(brandId);
        
        if (brand == null) {
            JOptionPane.showMessageDialog(this, "Không thể tìm thấy thông tin nhãn hàng!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa thông tin nhãn hàng", true);
        dialog.setLayout(new GridLayout(2, 2, 5, 5));
        
        JTextField nameField = new JTextField(brand.getBrandName());
        
        dialog.add(new JLabel("Tên nhãn hàng:"));
        dialog.add(nameField);
        
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        
        saveButton.addActionListener(e -> {
            brand.setBrandName(nameField.getText().trim());
            
            if (brandDAO.updateBrand(brand)) {
                refreshBrandTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteBrand(int selectedRow) {
        int brandId = (int) brandTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa nhãn hàng này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (brandDAO.deleteBrand(brandId)) {
                refreshBrandTable();
                JOptionPane.showMessageDialog(this, "Xóa nhãn hàng thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhãn hàng thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleBrandFunction(String function) {
        switch (function) {
            case "Thêm" -> showAddBrandDialog();
            case "Sửa" -> {
                int row = brandTable.getSelectedRow();
                if (row != -1) showEditBrandDialog(row);
                else JOptionPane.showMessageDialog(this, "Vui lòng chọn nhãn hàng cần sửa!");
            }
            case "Xóa" -> {
                int row = brandTable.getSelectedRow();
                if (row != -1) deleteBrand(row);
                else JOptionPane.showMessageDialog(this, "Vui lòng chọn nhãn hàng cần xóa!");
            }
            case "Làm mới" -> refreshBrandTable();
        }
    }

    private void handleStoreFunction(String function) {
        switch (function) {
            case "Thêm" -> showAddStoreDialog();
            case "Sửa" -> {
                int row = storeTable.getSelectedRow();
                if (row != -1) showEditStoreDialog(row);
                else JOptionPane.showMessageDialog(this, "Vui lòng chọn cửa hàng cần sửa!");
            }
            case "Xóa" -> {
                int row = storeTable.getSelectedRow();
                if (row != -1) deleteStore(row);
                else JOptionPane.showMessageDialog(this, "Vui lòng chọn cửa hàng cần xóa!");
            }
            case "Làm mới" -> refreshStoreTable();
        }
    }
    
    private void showStorePanel() {
        ((TitledBorder) ((JPanel) rightPanel.getComponent(0)).getBorder())
            .setTitle("Danh sách cửa hàng");
        
        String[] columnNames = {"ID", "Tên cửa hàng", "Số điện thoại", "Email", 
                              "Địa chỉ", "Thành phố", "Tỉnh/TP", "Mã bưu chính"};
        storeTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane scrollPane = new JScrollPane(storeTable);
        
        JPanel listPanel = (JPanel) rightPanel.getComponent(0);
        listPanel.removeAll();
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.revalidate();
        listPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}