package cdiofinal.client;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import cdiofinal.shared.InsufficientAccessException;
import cdiofinal.shared.RaavareDTO;

public class ListRaavareComposite extends Composite implements AsyncCallback<RaavareDTO[]>, NewElementCreatedCallback<RaavareDTO> {
	
	private final RaavareRPCInterfaceAsync database = (RaavareRPCInterfaceAsync)GWT.create(RaavareRPCInterface.class);


	interface ListRaavareUiBinder extends UiBinder<Widget, ListRaavareComposite> {}
	private static ListRaavareUiBinder listRaavareUiBinder = GWT.create(ListRaavareUiBinder.class);
	public @UiField(provided=true) CellTable<RaavareDTO> vPanel;
	private List<RaavareDTO> gui;
	
	public ListRaavareComposite()
	{
		vPanel = new CellTable<RaavareDTO>();
		vPanel.setVisibleRange(0, 1000000);
		initWidget(listRaavareUiBinder.createAndBindUi(this));
		gui = getLayoutList();
	}
	
	@UiHandler("newElement")
	public void onClick(ClickEvent e)
	{
		Popupcontainer p = new Popupcontainer(new NewRaavareComposite(this));
		p.show();
	}
	
	public List<RaavareDTO> getLayoutList() { //TODO: Show users when clicked
		Column<RaavareDTO, String> IDColumn = getIDColumn();
		Column<RaavareDTO, String> nameColumn = getNameColumn();
		Column<RaavareDTO, String> saveColumn = getButtonColumn("Gem");
		saveColumn.setFieldUpdater(new FieldUpdater<RaavareDTO, String>() {
					@Override
					  public void update(final int index, RaavareDTO object, String value) {
							database.updateRaavare(object, Token.getToken(), new AsyncCallback<Integer>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert(ErrorHandling.getError(caught));
									if(caught instanceof InsufficientAccessException)
									{
										gui.clear();
									}
								}

								@Override
								public void onSuccess(Integer result) {
									Window.alert("Succesfuldt updateret");
								}
								
							});
					  }
				});
				
		vPanel.addColumn(IDColumn, "ID");
		vPanel.addColumn(nameColumn, "Navn");
		vPanel.addColumn(saveColumn, "");
		
		ListDataProvider<RaavareDTO> raavareList = new ListDataProvider<RaavareDTO>();
		
		
		
		raavareList.addDataDisplay(vPanel);		
		
		return raavareList.getList();
	}

	private Column<RaavareDTO, String> getButtonColumn(final String value) {
		ButtonCell button = new ButtonCell();
		Column<RaavareDTO, String> buttonColumn = new Column<RaavareDTO, String>(button)
				{
					@Override
					public String getValue(RaavareDTO user)
					{
						return value;
					}
				};
				
		return buttonColumn;
	}
	
	private Column<RaavareDTO, String> getNameColumn() {
		EditTextCell nameCell = new EditTextCell();
		Column<RaavareDTO, String> nameColumn = new Column<RaavareDTO, String>(nameCell)
				{
					@Override
					public String getValue(RaavareDTO raavare) {
						return raavare.getRaavareNavn();
					}
				};
		nameColumn.setFieldUpdater(new FieldUpdater<RaavareDTO, String>(){

			  @Override
			public void update(int index, final RaavareDTO Raavare, final String value) {
				  		Raavare.setRaavareNavn(value);
			  }});
		return nameColumn;
	}
	
	private Column<RaavareDTO, String> getIDColumn(){
		TextCell IDCell = new TextCell();
		Column<RaavareDTO, String> IDColumn = new Column<RaavareDTO, String>(IDCell)
				{
					@Override
					public String getValue(RaavareDTO raavare) {
						return Integer.toString(raavare.getRaavareId());
					}
				};
		
		return IDColumn;
	}

	//Fired when the user clicks "list users"
	@Override
	public void onLoad() {
		database.getRaavareList(Token.getToken(), this);
	
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(ErrorHandling.getError(caught));
		gui.clear();
	}
	@Override
	public void onSuccess(RaavareDTO[] result) {
		if(result==null)
		{
			Window.alert("Ingen data modtaget.");
		}
		gui.clear();
		for (RaavareDTO RaavareDTO : result) {
			gui.add(RaavareDTO);
		}
	}

	@Override
	public void onElementCreated(RaavareDTO object) {
		gui.add(object);
		
	}
	


}
