package gui;

public class SeveralButtons {
	for ( int h = 0; h < rader; h++) {
		//"C:/Users/Public/Documents/Skole/Minesveiper/src/icons/"+name+".png"
		for (int j = 0; j < kolonner; j++) {
			final int x=h;
			final int y=j;
			buttons[h][j] = new JButton(new AbstractAction("", uapnet) {
				// This method is called when the button is pressed
				public void actionPerformed(ActionEvent evt) {

					//if(recordFirstClick()==2){
						//System.out.println(recordFirstClick());
						//if(bane[x][y]!='.'){
							//createBane();
							//return;
						//}
					//}
					//System.out.println(evt.getModifiers());
					if(evt.getModifiers()==18){
						buttons[x][y].setIcon(s);
						if(bane[x][y]=='b'){
							bombber--;
						}
						return;
					}
						try {
							if(bane[x][y]=='.'){
								apneMange(x,y);
								buttons[x][y].setIcon(ingenting);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						try {
							if(bane[x][y]=='1'){
								buttons[x][y].setIcon(en);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						try {
							if(bane[x][y]=='2'){
								buttons[x][y].setIcon(to);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						try {
							if(bane[x][y]=='3'){
								buttons[x][y].setIcon(tre);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						try {
							if(bane[x][y]=='4'){
								buttons[x][y].setIcon(fire);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						try {
							if(bane[x][y]=='b'){
								buttons[x][y].setIcon(bombe);
								JOptionPane.showMessageDialog(null, "du tapte");
								System.exit(0);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						if(sjekkVinner()==true){
							JOptionPane.showMessageDialog(null, "DU VANT");
						}
				}
			}
		);
			buttons[h][j].setSize(5, 5);
			buttons[h][j].setActionCommand(b[h][j]);
			
			panel.add(buttons[h][j]);
		}
	}
}
}
