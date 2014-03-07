package mainWindow;

import javax.swing.Box;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import applicationTools.FontManager;
import applicationTools.MultilineLabel;
import customWindow.CustomDialog;

public class TutorialPopUp extends CustomDialog{
	private JLabel lblTitle;
	private Box verticalBox;
	
	public TutorialPopUp(){
		lblTitle = new JLabel("Tutoriel");
		lblTitle.setFont(FontManager.getPopUpWindowTitle());
		
		verticalBox = Box.createVerticalBox();
		
		initGui();
	}
	
	private void initGui(){
		panelContent.setLayout(new MigLayout("", "[][]", "[][grow]"));
		
		panelContent.add(lblTitle, "cell 0 0 2 1, alignx center");
		panelContent.add(verticalBox, "cell 0 1 2 1,aligny top");
		
		panelContent.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam placerat lacus sit amet lectus tempor dapibus. Aenean mattis neque sit amet diam gravida scelerisque. In in rutrum leo. Phasellus molestie fermentum libero, vel ornare orci viverra ut. Duis congue mi ultricies congue auctor. Maecenas lobortis fermentum diam vel hendrerit. Fusce purus mi, laoreet at diam eget, posuere consectetur enim. Donec gravida accumsan diam, eu pretium augue lacinia ac. Vivamus gravida at felis eu pretium. In ullamcorper purus ac sapien lacinia, vel ultrices nisl hendrerit. Morbi a nisl turpis. Nulla at dapibus urna. Vivamus varius, enim vitae laoreet consectetur, ligula velit dignissim orci, at scelerisque dolor mauris nec dolor. Suspendisse suscipit erat libero, sit amet condimentum augue consequat eget.Curabitur nec augue at mauris mattis posuere tempor a ligula. Aliquam auctor nunc tempor pellentesque sagittis. Cras feugiat rutrum risus at vehicula. Maecenas condimentum tortor justo, ut laoreet diam accumsan pretium. Donec fermentum nibh eget nisi gravida, vitae cursus nisi gravida. In vestibulum eleifend posuere. Mauris orci purus, congue vel erat at, malesuada malesuada lacus. Curabitur iaculis porta feugiat. Morbi sit amet sapien rutrum nunc consectetur interdum at id mi. Curabitur cursus est in metus tempor, at consequat sapien viverra. Morbi vitae adipiscing sem, luctus dapibus magna. Sed nec accumsan tellus. Curabitur a velit euismod metus dictum posuere ac vitae diam. Sed fringilla ultricies ornare.Nullam sed elit quam. Integer molestie, enim ac sagittis dictum, elit turpis tincidunt nunc, id sodales libero risus id diam. Nam non consectetur dui. Sed adipiscing pharetra magna, sed tempor lacus. Etiam ac felis mollis tellus imperdiet accumsan. Nam erat massa, blandit et nulla sed, venenatis lacinia neque. Ut risus eros, rhoncus nec purus in, laoreet euismod arcu. Maecenas vitae dapibus nunc. Ut venenatis quam vel felis eleifend hendrerit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam pulvinar odio nibh, et commodo mauris aliquet id. Donec cursus ullamcorper euismod. Nunc ornare dui eget lorem mollis, eget lobortis velit aliquet. ", dialogWindow.getWidth()-35, FontManager.getQuestionZoneSubItem()), dialogWindow.getWidth()-30, dialogWindow.getHeight(), true), "cell 0 2");
	
	}
	
	public void showWindow(){
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}
}
