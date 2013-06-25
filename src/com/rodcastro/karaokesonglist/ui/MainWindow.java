package com.rodcastro.karaokesonglist.ui;

import com.rodcastro.karaokesonglist.KaraokePlayer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultRowSorter;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.rodcastro.karaokesonglist.Main;
import com.rodcastro.karaokesonglist.models.Song;
import com.rodcastro.karaokesonglist.models.SongRepository;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

/**
 *
 * @author rodcastro
 */
public class MainWindow extends javax.swing.JFrame {

    private SplashWindow splash;
    private DefaultTableModel modelSongs;
    private DefaultTableModel modelInvalidSongs;
    private boolean editMode = false;
    private LoadingBarListener listener;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        splash = new SplashWindow(this);
        splash.setVisible(true);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        createTableModels();
        tableSongs.setAutoCreateRowSorter(true);
        btnEditName.setVisible(false);
        btnEditArtist.setVisible(false);
        btnSwitchNames.setVisible(false);
        try {
            setIconImage(ImageIO.read(new File("resources/images/icon.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        updateQueueControls();
        progressBar.setVisible(false);
        progressBar.setString("Preparando...");
        listener = new LoadingBarListener() {
            @Override
            public void onUpdateBar(int value, String message) {
                progressBar.setValue(value);
                progressBar.setString((int) Math.ceil(progressBar.getPercentComplete() * 100) + "% - " + message);
            }

            @Override
            public void onChangeMaxValue(int maxValue) {
                progressBar.setMaximum(maxValue);
            }

            @Override
            public void onFinish() {
                searchSongs("");
                DefaultRowSorter sorter = ((DefaultRowSorter) tableSongs.getRowSorter());
                ArrayList list = new ArrayList();
                list.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                sorter.setSortKeys(list);
                progressBar.setValue(0);
                progressBar.setVisible(false);
                progressBar.setString("Preparando...");
            }
        };
    }

    public void loadSongs() {
        splash.loadSongs();
    }

    public void searchSongs(String search) {
        btnSearch.setEnabled(false);
        txtSearch.setEditable(false);
        boolean searchName = checkSongName.isSelected();
        boolean searchArtist = checkArtist.isSelected();
        boolean searchPack = checkPack.isSelected();
        List sortOrder = ((DefaultRowSorter) tableSongs.getRowSorter()).getSortKeys();
        SongRepository repository = Main.getRepository();
        String[][] filteredSongs = repository.findSongs(search, searchName, searchArtist, searchPack);
        modelSongs.setDataVector(filteredSongs, new String[]{"Música", "Artista", "Pack", "Unique Id"});
        tableSongs.setAutoCreateRowSorter(true);
        tableSongs.setModel(modelSongs);
        tableSongs.getColumnModel().removeColumn(tableSongs.getColumnModel().getColumn(3));
        DefaultRowSorter sorter = ((DefaultRowSorter) tableSongs.getRowSorter());
        sorter.setSortKeys(sortOrder);
        btnSearch.setEnabled(true);
        txtSearch.setEditable(true);
        lblSongCount.setText("Total de músicas: " + modelSongs.getRowCount());
    }

    private void createTableModels() {
        modelSongs = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelInvalidSongs = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void updateQueueList() {
        DefaultListModel<String> model = new DefaultListModel<String>();
        LinkedList<Song> songQueue = KaraokePlayer.getInstance().getSongs();
        int i = 1;
        for (Song song : songQueue) {
            model.addElement(i++ + " - " + song.getFormattedSongName());
        }
        listQueue.setModel(model);
    }

    private void updateQueueControls() {
        int selected = listQueue.getSelectedIndex();
        int size = listQueue.getModel().getSize();
        btnQueuePlay.setEnabled(size > 0);
        btnQueueRemove.setEnabled(selected > -1);
        btnQueueUp.setEnabled(selected > 0);
        btnQueueDown.setEnabled(selected > -1 && selected < size - 1);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        splitPanel = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        pnSideBar = new javax.swing.JPanel();
        btnEditMode = new javax.swing.JToggleButton();
        btnPlayMode = new javax.swing.JToggleButton();
        btnShowInvalid = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listQueue = new javax.swing.JList();
        btnQueuePlay = new javax.swing.JButton();
        btnQueueUp = new javax.swing.JButton();
        btnQueueDown = new javax.swing.JButton();
        btnQueueRemove = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        pnSearch = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        checkSongName = new javax.swing.JCheckBox();
        checkArtist = new javax.swing.JCheckBox();
        checkPack = new javax.swing.JCheckBox();
        pnTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSongs = new javax.swing.JTable();
        btnEditArtist = new javax.swing.JButton();
        btnEditName = new javax.swing.JButton();
        btnSwitchNames = new javax.swing.JButton();
        lblSongCount = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemExit = new javax.swing.JMenuItem();
        menuPlayControl = new javax.swing.JMenu();
        itemPlayNext = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenu();
        itemChangeWorkingPath = new javax.swing.JMenuItem();
        itemChangeKaraokePath = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sunfly Song Browser");
        setMinimumSize(new java.awt.Dimension(850, 500));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon("/home/rodcastro/workspace/karaoke_song_checker/resources/images/logo.png")); // NOI18N
        lblLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        splitPanel.setBorder(null);
        splitPanel.setDividerLocation(240);

        jPanel5.setPreferredSize(new java.awt.Dimension(300, 100));

        pnSideBar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        pnSideBar.setMaximumSize(new java.awt.Dimension(300, 32767));
        pnSideBar.setMinimumSize(new java.awt.Dimension(200, 100));
        pnSideBar.setPreferredSize(new java.awt.Dimension(200, 500));

        btnEditMode.setText("Editar músicas");
        btnEditMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditModeActionPerformed(evt);
            }
        });

        btnPlayMode.setSelected(true);
        btnPlayMode.setText("Buscar e tocar músicas");
        btnPlayMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayModeActionPerformed(evt);
            }
        });

        btnShowInvalid.setText("Exibir Músicas Inválidas");
        btnShowInvalid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowInvalidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnSideBarLayout = new javax.swing.GroupLayout(pnSideBar);
        pnSideBar.setLayout(pnSideBarLayout);
        pnSideBarLayout.setHorizontalGroup(
            pnSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowInvalid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPlayMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnSideBarLayout.setVerticalGroup(
            pnSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlayMode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditMode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShowInvalid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fila de músicas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        listQueue.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listQueue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listQueueMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listQueue);

        btnQueuePlay.setIcon(new javax.swing.ImageIcon("/home/rodcastro/workspace/karaoke_song_checker/resources/images/play.png")); // NOI18N
        btnQueuePlay.setText("Play");
        btnQueuePlay.setToolTipText("");
        btnQueuePlay.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnQueuePlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueuePlayActionPerformed(evt);
            }
        });

        btnQueueUp.setText("▲");
        btnQueueUp.setMargin(new java.awt.Insets(2, 3, 2, 3));
        btnQueueUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueueUpActionPerformed(evt);
            }
        });

        btnQueueDown.setText("▼");
        btnQueueDown.setMargin(new java.awt.Insets(2, 3, 2, 3));
        btnQueueDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueueDownActionPerformed(evt);
            }
        });

        btnQueueRemove.setText("☓");
        btnQueueRemove.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnQueueRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueueRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnQueuePlay, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQueueUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQueueDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQueueRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQueuePlay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnQueueUp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQueueDown, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQueueRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnQueuePlay.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnSideBar, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(pnSideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPanel.setLeftComponent(jPanel5);

        pnSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Músicas"));
        pnSearch.setPreferredSize(new java.awt.Dimension(642, 80));

        jLabel1.setText("Pesquisar:");

        txtSearch.setPreferredSize(new java.awt.Dimension(100, 27));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        btnSearch.setText("Buscar");
        btnSearch.setPreferredSize(new java.awt.Dimension(65, 30));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        checkSongName.setSelected(true);
        checkSongName.setText("Nome da música");
        checkSongName.setPreferredSize(new java.awt.Dimension(150, 23));

        checkArtist.setSelected(true);
        checkArtist.setText("Nome do Artista");
        checkArtist.setPreferredSize(new java.awt.Dimension(150, 23));

        checkPack.setSelected(true);
        checkPack.setText("Nome do Pacote");
        checkPack.setPreferredSize(new java.awt.Dimension(150, 23));

        javax.swing.GroupLayout pnSearchLayout = new javax.swing.GroupLayout(pnSearch);
        pnSearch.setLayout(pnSearchLayout);
        pnSearchLayout.setHorizontalGroup(
            pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSearchLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnSearchLayout.createSequentialGroup()
                        .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(pnSearchLayout.createSequentialGroup()
                                .addComponent(checkSongName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnSearchLayout.setVerticalGroup(
            pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSearchLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkSongName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Músicas"));

        tableSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Pack", "Artista", "Música"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSongs.getTableHeader().setResizingAllowed(false);
        tableSongs.getTableHeader().setReorderingAllowed(false);
        tableSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSongsMouseClicked(evt);
            }
        });
        tableSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tableSongsKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tableSongs);
        tableSongs.getColumnModel().getColumn(0).setResizable(false);
        tableSongs.getColumnModel().getColumn(1).setResizable(false);
        tableSongs.getColumnModel().getColumn(2).setResizable(false);

        btnEditArtist.setText("Editar Artista");
        btnEditArtist.setPreferredSize(new java.awt.Dimension(200, 29));
        btnEditArtist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditArtistActionPerformed(evt);
            }
        });

        btnEditName.setText("Editar Nome da Música");
        btnEditName.setPreferredSize(new java.awt.Dimension(200, 29));
        btnEditName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditNameActionPerformed(evt);
            }
        });

        btnSwitchNames.setText("Trocar Artista <> Nome");
        btnSwitchNames.setPreferredSize(new java.awt.Dimension(200, 29));
        btnSwitchNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchNamesActionPerformed(evt);
            }
        });

        lblSongCount.setText("Total de músicas: 0");

        progressBar.setStringPainted(true);

        javax.swing.GroupLayout pnTableLayout = new javax.swing.GroupLayout(pnTable);
        pnTable.setLayout(pnTableLayout);
        pnTableLayout.setHorizontalGroup(
            pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTableLayout.createSequentialGroup()
                        .addComponent(lblSongCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnTableLayout.createSequentialGroup()
                        .addComponent(btnSwitchNames, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditName, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnTableLayout.setVerticalGroup(
            pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSwitchNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSongCount)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(pnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPanel.setRightComponent(jPanel6);

        menuFile.setText("Arquivo");

        itemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        itemExit.setText("Sair");
        itemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExitActionPerformed(evt);
            }
        });
        menuFile.add(itemExit);

        jMenuBar1.add(menuFile);

        menuPlayControl.setText("Músicas");

        itemPlayNext.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_MASK));
        itemPlayNext.setText("Tocar Próxima");
        itemPlayNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPlayNextActionPerformed(evt);
            }
        });
        menuPlayControl.add(itemPlayNext);

        jMenuBar1.add(menuPlayControl);

        menuSettings.setText("Configurações");
        menuSettings.setBorderPainted(true);

        itemChangeWorkingPath.setText("Trocar Pasta das Músicas");
        itemChangeWorkingPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemChangeWorkingPathActionPerformed(evt);
            }
        });
        menuSettings.add(itemChangeWorkingPath);

        itemChangeKaraokePath.setText("Trocar Pasta do Player");
        menuSettings.add(itemChangeKaraokePath);

        jMenuBar1.add(menuSettings);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(splitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(splitPanel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchSongs(txtSearch.getText());
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchSongs(txtSearch.getText());
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnEditArtistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditArtistActionPerformed
        SongRepository repository = Main.getRepository();
        int[] rowsIds = tableSongs.getSelectedRows();
        TableModel model = tableSongs.getModel();
        List<Song> selectedSongs = new ArrayList<Song>();
        for (int i : rowsIds) {
            Object value = model.getValueAt(tableSongs.convertRowIndexToModel(i), 3);
            if (value != null) {
                int uniqueId = Integer.parseInt(value.toString());
                selectedSongs.add(repository.findSong(uniqueId));
            }
        }
        if (selectedSongs.size() == 1) {
            Song selected = selectedSongs.get(0);
            if (selected.isValid()) {
                String newArtist = JOptionPane.showInputDialog(this, "Digite o nome do artista:", selected.getFormattedArtist());
                if (newArtist != null) {
                    newArtist = newArtist.replace("-", "*");
                    selected.setArtist(newArtist);
                    selected.updateFile();
                    model.setValueAt(selected.getFormattedArtist(), tableSongs.convertRowIndexToModel(rowsIds[0]), 1);
                    model.setValueAt(selected.getUniqueId(), tableSongs.convertRowIndexToModel(rowsIds[0]), 3);
                    JOptionPane.showMessageDialog(this, "Artista alterado!", "Sunfly Song List", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Música inválida, não é possível fazer alterações", "Sunfly Song List", JOptionPane.ERROR_MESSAGE);
            }
        } else if (selectedSongs.size() > 1) {
            String newArtist = JOptionPane.showInputDialog(this, "Digite o nome do artista para todas as músicas selecionadas:");
            if (newArtist != null) {
                newArtist = newArtist.replace("-", "*");
                int invalid = 0;
                for (int i = 0; i < selectedSongs.size(); i++) {
                    Song song = selectedSongs.get(i);
                    if (song.isValid()) {
                        song.setArtist(newArtist);
                        song.updateFile();
                        model.setValueAt(song.getFormattedArtist(), tableSongs.convertRowIndexToModel(rowsIds[i]), 1);
                        model.setValueAt(song.getUniqueId(), tableSongs.convertRowIndexToModel(rowsIds[i]), 3);
                    } else {
                        invalid++;
                    }
                }
                if (invalid == 0) {
                    JOptionPane.showMessageDialog(this, "Artistas alterados!", "Sunfly Song List", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Nem todas as músicas puderam ser alteradas, pois " + invalid + " delas são inválidas!", "Sunfly Song List", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        tableSongs.requestFocus();
    }//GEN-LAST:event_btnEditArtistActionPerformed

    private void btnEditNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditNameActionPerformed
        SongRepository repository = Main.getRepository();
        int[] rowsIds = tableSongs.getSelectedRows();
        TableModel model = tableSongs.getModel();
        List<Song> selectedSongs = new ArrayList<Song>();
        for (int i : rowsIds) {
            Object value = model.getValueAt(tableSongs.convertRowIndexToModel(i), 3);
            if (value != null) {
                int uniqueId = Integer.parseInt(value.toString());
                selectedSongs.add(repository.findSong(uniqueId));
            }
        }
        if (selectedSongs.size() == 1) {
            Song selected = selectedSongs.get(0);
            if (selected.isValid()) {
                String newSongName = JOptionPane.showInputDialog(this, "Digite o nome da música:", selected.getFormattedSongName());
                if (newSongName != null) {
                    newSongName = newSongName.replace("-", "*");
                    selected.setSongName(newSongName);
                    selected.updateFile();
                    model.setValueAt(selected.getFormattedSongName(), tableSongs.convertRowIndexToModel(rowsIds[0]), 0);
                    model.setValueAt(selected.getUniqueId(), tableSongs.convertRowIndexToModel(rowsIds[0]), 3);
                    JOptionPane.showMessageDialog(this, "Nome da música alterada!", "Sunfly Song List", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Música inválida, não é possível fazer alterações", "Sunfly Song List", JOptionPane.ERROR_MESSAGE);
            }
        } else if (selectedSongs.size() > 1) {
            String newSongName = JOptionPane.showInputDialog(this, "Digite o nome da música para todas as músicas selecionadas:");
            if (newSongName != null) {
                newSongName = newSongName.replace("-", "*");
                int invalid = 0;
                for (int i = 0; i < selectedSongs.size(); i++) {
                    Song song = selectedSongs.get(i);
                    if (song.isValid()) {
                        song.setSongName(newSongName);
                        song.updateFile();
                        model.setValueAt(song.getFormattedSongName(), tableSongs.convertRowIndexToModel(rowsIds[i]), 0);
                        model.setValueAt(song.getUniqueId(), tableSongs.convertRowIndexToModel(rowsIds[i]), 3);
                    } else {
                        invalid++;
                    }
                }
                if (invalid == 0) {
                    JOptionPane.showMessageDialog(this, "Nomes alterados!", "Sunfly Song List", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Nem todas as músicas puderam ser alteradas, pois " + invalid + " delas são inválidas!", "Sunfly Song List", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        tableSongs.requestFocus();
    }//GEN-LAST:event_btnEditNameActionPerformed

    private void btnSwitchNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchNamesActionPerformed
        SongRepository repository = Main.getRepository();
        int[] rowsIds = tableSongs.getSelectedRows();
        TableModel model = tableSongs.getModel();
        List<Song> selectedSongs = new ArrayList<Song>();
        for (int i : rowsIds) {
            Object value = model.getValueAt(tableSongs.convertRowIndexToModel(i), 3);
            if (value != null) {
                int uniqueId = Integer.parseInt(value.toString());
                selectedSongs.add(repository.findSong(uniqueId));
            }
        }
        if (selectedSongs.size() == 1) {
            Song selected = selectedSongs.get(0);
            if (selected.isValid()) {
                String newSongName = selected.getArtist();
                selected.setArtist(selected.getSongName());
                selected.setSongName(newSongName);
                selected.updateFile();
                model.setValueAt(selected.getFormattedArtist(), tableSongs.convertRowIndexToModel(rowsIds[0]), 1);
                model.setValueAt(selected.getFormattedSongName(), tableSongs.convertRowIndexToModel(rowsIds[0]), 0);
                model.setValueAt(selected.getUniqueId(), tableSongs.convertRowIndexToModel(rowsIds[0]), 3);
            } else {
                JOptionPane.showMessageDialog(this, "Música inválida, não é possível fazer alterações", "Sunfly Song List", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Só é possível trocar nome e artista de uma música por vez", "Sunfly Song List", JOptionPane.ERROR_MESSAGE);
        }
        tableSongs.requestFocus();
    }//GEN-LAST:event_btnSwitchNamesActionPerformed

    private void btnEditModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditModeActionPerformed
        editMode = true;
        btnEditName.setVisible(editMode);
        btnEditArtist.setVisible(editMode);
        btnSwitchNames.setVisible(editMode);
        btnEditMode.setSelected(editMode);
        btnPlayMode.setSelected(!editMode);
    }//GEN-LAST:event_btnEditModeActionPerformed

    private void btnPlayModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayModeActionPerformed
        editMode = false;
        btnEditName.setVisible(editMode);
        btnEditArtist.setVisible(editMode);
        btnSwitchNames.setVisible(editMode);
        btnEditMode.setSelected(editMode);
        btnPlayMode.setSelected(!editMode);
    }//GEN-LAST:event_btnPlayModeActionPerformed

    private void btnShowInvalidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowInvalidActionPerformed
        if (btnShowInvalid.isSelected()) {
            SongRepository repository = Main.getRepository();
            String[][] invalidSongs = repository.findInvalidSongs("");
            modelInvalidSongs.setDataVector(invalidSongs, new String[]{"Nome do arquivo"});
            tableSongs.setAutoCreateRowSorter(true);
            tableSongs.setModel(modelInvalidSongs);
        } else {
            searchSongs("");
        }
    }//GEN-LAST:event_btnShowInvalidActionPerformed

    private void tableSongsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSongsMouseClicked
        if (evt.getClickCount() == 2) {
            SongRepository repository = Main.getRepository();
            int[] rowsIds = tableSongs.getSelectedRows();
            TableModel model = tableSongs.getModel();
            Song selectedSong = null;
            Object value = model.getValueAt(tableSongs.convertRowIndexToModel(rowsIds[0]), 3);
            if (value != null) {
                int uniqueId = Integer.parseInt(value.toString());
                selectedSong = repository.findSong(uniqueId);
            }
            if (selectedSong != null) {
                KaraokePlayer.getInstance().addToQueue(selectedSong);
                updateQueueList();
                updateQueueControls();
            }
        }
    }//GEN-LAST:event_tableSongsMouseClicked

    private void btnQueuePlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueuePlayActionPerformed
        KaraokePlayer.getInstance().playNext();
        updateQueueList();
        updateQueueControls();
    }//GEN-LAST:event_btnQueuePlayActionPerformed

    private void btnQueueRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueueRemoveActionPerformed
        int selected = listQueue.getSelectedIndex();
        if (selected >= 0) {
            KaraokePlayer.getInstance().getSongs().remove(selected);
            updateQueueList();
            if (listQueue.getModel().getSize() > selected) {
                listQueue.setSelectedIndex(selected);
            } else if (listQueue.getModel().getSize() > 0) {
                listQueue.setSelectedIndex(selected - 1);
            }
            updateQueueControls();
        }
    }//GEN-LAST:event_btnQueueRemoveActionPerformed

    private void btnQueueUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueueUpActionPerformed
        int selected = listQueue.getSelectedIndex();
        if (selected >= 1) {
            LinkedList<Song> songs = KaraokePlayer.getInstance().getSongs();
            Song swap = songs.get(selected - 1);
            songs.set(selected - 1, songs.get(selected));
            songs.set(selected, swap);
            updateQueueList();
            listQueue.setSelectedIndex(selected - 1);
            updateQueueControls();
        }
    }//GEN-LAST:event_btnQueueUpActionPerformed

    private void btnQueueDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueueDownActionPerformed
        int selected = listQueue.getSelectedIndex();
        if (selected >= 0 && selected < (listQueue.getModel().getSize() - 1)) {
            LinkedList<Song> songs = KaraokePlayer.getInstance().getSongs();
            Song swap = songs.get(selected + 1);
            songs.set(selected + 1, songs.get(selected));
            songs.set(selected, swap);
            updateQueueList();
            listQueue.setSelectedIndex(selected + 1);
            updateQueueControls();
        }
    }//GEN-LAST:event_btnQueueDownActionPerformed

    private void listQueueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listQueueMouseClicked
        updateQueueControls();
    }//GEN-LAST:event_listQueueMouseClicked

    private void tableSongsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableSongsKeyTyped
        System.out.println(evt.getKeyChar());
        switch (evt.getKeyChar()) {
            case '\n':
                SongRepository repository = Main.getRepository();
                int[] rowsIds = tableSongs.getSelectedRows();
                TableModel model = tableSongs.getModel();
                Song selectedSong = null;
                Object value = model.getValueAt(tableSongs.convertRowIndexToModel(rowsIds[0]), 3);
                if (value != null) {
                    int uniqueId = Integer.parseInt(value.toString());
                    selectedSong = repository.findSong(uniqueId);
                }
                if (selectedSong != null) {
                    KaraokePlayer.getInstance().addToQueue(selectedSong);
                    updateQueueList();
                    updateQueueControls();
                }
                break;
        }

    }//GEN-LAST:event_tableSongsKeyTyped

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_itemExitActionPerformed

    private void itemPlayNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPlayNextActionPerformed
        KaraokePlayer.getInstance().playNext();
        updateQueueList();
        updateQueueControls();
    }//GEN-LAST:event_itemPlayNextActionPerformed

    private void itemChangeWorkingPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemChangeWorkingPathActionPerformed
        Main.openSongFolderDialog();
        progressBar.setVisible(true);
        Main.getRepository().setListener(listener);
        Main.reloadRepository();
    }//GEN-LAST:event_itemChangeWorkingPathActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditArtist;
    private javax.swing.JToggleButton btnEditMode;
    private javax.swing.JButton btnEditName;
    private javax.swing.JToggleButton btnPlayMode;
    private javax.swing.JButton btnQueueDown;
    private javax.swing.JButton btnQueuePlay;
    private javax.swing.JButton btnQueueRemove;
    private javax.swing.JButton btnQueueUp;
    private javax.swing.JButton btnSearch;
    private javax.swing.JToggleButton btnShowInvalid;
    private javax.swing.JButton btnSwitchNames;
    private javax.swing.JCheckBox checkArtist;
    private javax.swing.JCheckBox checkPack;
    private javax.swing.JCheckBox checkSongName;
    private javax.swing.JMenuItem itemChangeKaraokePath;
    private javax.swing.JMenuItem itemChangeWorkingPath;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemPlayNext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSongCount;
    private javax.swing.JList listQueue;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuPlayControl;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JPanel pnSearch;
    private javax.swing.JPanel pnSideBar;
    private javax.swing.JPanel pnTable;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSplitPane splitPanel;
    private javax.swing.JTable tableSongs;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
