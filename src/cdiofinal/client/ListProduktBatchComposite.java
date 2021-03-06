package cdiofinal.client;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
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
import cdiofinal.shared.ProduktBatchDTO;


public class ListProduktBatchComposite extends Composite implements AsyncCallback<ProduktBatchDTO[]>, NewElementCreatedCallback<ProduktBatchDTO> {
	
	private final ProduktBatchRPCInterfaceAsync database = (ProduktBatchRPCInterfaceAsync)GWT.create(ProduktBatchRPCInterface.class);
	interface ListProduktBatchUiBinder extends UiBinder<Widget, ListProduktBatchComposite> {}
	private static ListProduktBatchUiBinder listProduktBatchUiBinder = GWT.create(ListProduktBatchUiBinder.class);
	
	public @UiField(provided=true) CellTable<ProduktBatchDTO> vPanel;
	private List<ProduktBatchDTO> gui;
	
	public ListProduktBatchComposite()
	{
		vPanel = new CellTable<ProduktBatchDTO>();
		vPanel.setVisibleRange(0, 1000000);
		initWidget(listProduktBatchUiBinder.createAndBindUi(this));
		gui = getLayoutList();
	}
	
	@UiHandler("newElement")
	public void onClick(ClickEvent e)
	{
		Popupcontainer p = new Popupcontainer(new NewProduktbatchComposite(this));
		p.show();
	}
	
	public List<ProduktBatchDTO> getLayoutList() { //TODO: Show users when clicked
		Column<ProduktBatchDTO, String> pbIDColumn = getPbIDColumn();
		//IDColumn.setSortable(true);
		Column<ProduktBatchDTO, String> statusColumn = getStatusColumn();
		//nameColumn.setSortable(true);
		Column<ProduktBatchDTO, String> receptIDColumn = getReceptIDColumn();
		//IDColumn.setSortable(true);
		Column<ProduktBatchDTO, String> saveColumn = getButtonColumn("Gem");
		saveColumn.setFieldUpdater(new FieldUpdater<ProduktBatchDTO, String>() {
					@Override
					  public void update(final int index, ProduktBatchDTO object, String value) {
							database.updateProduktBatch(object, Token.getToken(), new AsyncCallback<Integer>() {
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
									Window.alert("Succesfuldt opdateret");
								}
								
							});
					  }
				});
		Column<ProduktBatchDTO, String> printColumn = getButtonColumn("Print");
		printColumn.setFieldUpdater(new FieldUpdater<ProduktBatchDTO, String>() {
			@Override
			  public void update(final int index, ProduktBatchDTO object, String value) {
					Window.open(GWT.getHostPageBaseURL() + "cdio_final/batchStatus?pbID="+object.getPbId() , "Printout", "");
			  }
		});
		
		vPanel.addColumn(pbIDColumn, "ProduktBatch ID");
		vPanel.addColumn(statusColumn, "Status");
		vPanel.addColumn(receptIDColumn, "Recept ID");
		vPanel.addColumn(saveColumn, "");
		vPanel.addColumn(printColumn, "");
		
		
		ListDataProvider<ProduktBatchDTO> produktbatchList = new ListDataProvider<ProduktBatchDTO>();
		
		
		
		produktbatchList.addDataDisplay(vPanel);		
		
		return produktbatchList.getList();
	}

	private Column<ProduktBatchDTO, String> getButtonColumn(final String value) {
		ButtonCell button = new ButtonCell();
		Column<ProduktBatchDTO, String> buttonColumn = new Column<ProduktBatchDTO, String>(button)
				{
					@Override
					public String getValue(ProduktBatchDTO user)
					{
						return value;
					}
				};
				
		return buttonColumn;
	}



	private Column<ProduktBatchDTO, String> getPbIDColumn() {
		TextCell pbIDCell = new TextCell();
		Column<ProduktBatchDTO, String> pbIDColumn = new Column<ProduktBatchDTO, String>(pbIDCell)
				{
					@Override
					public String getValue(ProduktBatchDTO produktbatch) {
						return Integer.toString(produktbatch.getPbId());
					}
				};
				
				return pbIDColumn;
	}
	
	private Column<ProduktBatchDTO, String> getStatusColumn() {
		final String[] statuses = new String[] {"Startet", "I Produktion", "Afsluttet"};
		SelectionCell statusCell = new SelectionCell(Arrays.asList(statuses));
		Column<ProduktBatchDTO, String> statusColumn = new Column<ProduktBatchDTO, String>(statusCell)
				{
					@Override
					public String getValue(ProduktBatchDTO produktbatch) {
						try
						{
						return statuses[produktbatch.getStatus()-1];
						}
						catch(IndexOutOfBoundsException e)
						{
							return "Ukendt(" + (produktbatch.getStatus()-1) + ")";
						}
					}
				};
		statusColumn.setFieldUpdater(new FieldUpdater<ProduktBatchDTO, String>(){

			  @Override
			public void update(int index, final ProduktBatchDTO produktbatch, final String value) {
				  
				  		switch(value)
						{
						case "Startet":
							produktbatch.setStatus(1);
							break;
						case "I Produktion":
							produktbatch.setStatus(2);
							break;
						case "Afsluttet":
							produktbatch.setStatus(3);
							break;
						
						}
			  }});
		return statusColumn;
	}
	
	private Column<ProduktBatchDTO, String> getReceptIDColumn() {
		TextCell receptIDCell = new TextCell();
		Column<ProduktBatchDTO, String> receptIDColumn = new Column<ProduktBatchDTO, String>(receptIDCell)
				{
					@Override
					public String getValue(ProduktBatchDTO produktbatch) {
						return Integer.toString(produktbatch.getReceptId());
					}
				};
				
		return receptIDColumn;
	}

	//Fired when the user clicks "list produktbatch"
	@Override
	public void onLoad() {
		database.getProduktBatchList(Token.getToken(), this);
	
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(ErrorHandling.getError(caught));
		gui.clear();
	}
	
	@Override
	public void onSuccess(ProduktBatchDTO[] result) {
		if(result==null)
		{
			Window.alert("Ingen data modtaget.");
		}
		gui.clear();
		for (ProduktBatchDTO produktbatchDTO : result) {
			gui.add(produktbatchDTO);
		}
	}

	@Override
	public void onElementCreated(ProduktBatchDTO object) {
		gui.add(object);
		
	}
	


}
