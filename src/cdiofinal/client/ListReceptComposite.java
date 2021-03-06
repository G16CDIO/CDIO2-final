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
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import cdiofinal.shared.InsufficientAccessException;
import cdiofinal.shared.ReceptDTO;

public class ListReceptComposite extends Composite implements AsyncCallback<ReceptDTO[]>, NewElementCreatedCallback<ReceptDTO> {
	
	private final ReceptRPCInterfaceAsync database = (ReceptRPCInterfaceAsync)GWT.create(ReceptRPCInterface.class);
	
	interface ListReceptUiBinder extends UiBinder<Widget, ListReceptComposite> {}
	private static ListReceptUiBinder listReceptUiBinder = GWT.create(ListReceptUiBinder.class);
	public @UiField(provided=true) CellTable<ReceptDTO> vPanel;
	private List<ReceptDTO> gui;
	
	public ListReceptComposite()
	{
		vPanel = new CellTable<ReceptDTO>();
		vPanel.setVisibleRange(0, 1000000);
		initWidget(listReceptUiBinder.createAndBindUi(this));
		gui = getLayoutList();
	}
	
	@UiHandler("newElement")
	public void onClick(ClickEvent e)
	{
		Popupcontainer p = new Popupcontainer(new NewReceptComposite(this));
		p.show();
	}
	
	public List<ReceptDTO> getLayoutList() { //TODO: Show users when clicked
		Column<ReceptDTO, String> receptIDColumn = getReceptIDColumn();
		//CPRColumn.setSortable(true);
		Column<ReceptDTO, String> receptNameColumn = getReceptNameColumn();
		Column<ReceptDTO, String> saveColumn = getButtonColumn("Gem");
		Column<ReceptDTO, String> componentColumn = getButtonColumn("Komponenter");
		saveColumn.setFieldUpdater(new FieldUpdater<ReceptDTO, String>() {
					@Override
					  public void update(final int index, ReceptDTO object, String value) {
							database.updateRecept(object, Token.getToken(), new AsyncCallback<Integer>() {
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
									Window.alert("Successfuldt opdateret");
								}
								
							});
					  }
				});
		componentColumn.setFieldUpdater(new FieldUpdater<ReceptDTO, String>() {
			@Override
			  public void update(final int index, ReceptDTO object, String value) {
				ListReceptKompComposite reckomp = new ListReceptKompComposite(object.getReceptId());
				RootPanel.get("contents").clear();
				RootPanel.get("contents").add(reckomp);
			  }
		});
				
		vPanel.addColumn(receptIDColumn, "Recept ID");
		vPanel.addColumn(receptNameColumn, "Recept Navn");
		vPanel.addColumn(saveColumn, "");
		vPanel.addColumn(componentColumn, "");
		
		
		ListDataProvider<ReceptDTO> receptList = new ListDataProvider<ReceptDTO>();
		
		
		
		receptList.addDataDisplay(vPanel);		
		
		return receptList.getList();
	}

	private Column<ReceptDTO, String> getButtonColumn(final String value) {
		ButtonCell button = new ButtonCell();
		Column<ReceptDTO, String> buttonColumn = new Column<ReceptDTO, String>(button)
				{
					@Override
					public String getValue(ReceptDTO recept)
					{
						return value;
					}
				};
				
		return buttonColumn;
	}

	

	private Column<ReceptDTO, String> getReceptIDColumn() {
		TextCell idCell = new TextCell();
		Column<ReceptDTO, String> idColumn = new Column<ReceptDTO, String>(idCell)
				{
					@Override
					public String getValue(ReceptDTO recept) {
						return Integer.toString(recept.getReceptId());
					}
				};
				
		return idColumn;
	}

	private Column<ReceptDTO, String> getReceptNameColumn() {
		EditTextCell nameCell = new EditTextCell();
		Column<ReceptDTO, String> nameColumn = new Column<ReceptDTO, String>(nameCell)
				{
					@Override
					public String getValue(ReceptDTO recept) {
						return recept.getReceptNavn();
					}
				};
				nameColumn.setFieldUpdater(new FieldUpdater<ReceptDTO, String>(){

					  @Override
					public void update(int index, final ReceptDTO Recept, final String value) {
						  		Recept.setReceptNavn(value);
					  }});
		return nameColumn;
	}

	//Fired when the recept clicks "list recepts"
	@Override
	public void onLoad() {
		database.getReceptList(Token.getToken(), this);
	
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(ErrorHandling.getError(caught));
		gui.clear();
	}
	@Override
	public void onSuccess(ReceptDTO[] result) {
		if(result==null)
		{
			Window.alert("Ingen data recieved.");
		}
		gui.clear();
		for (ReceptDTO ReceptDTO : result) {
			gui.add(ReceptDTO);
		}
	}

	@Override
	public void onElementCreated(ReceptDTO object) {
		gui.add(object);
		
	}
	


}

