package br.usp.ime.rusp.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.ime.usp.R;
import br.usp.ime.rusp.core.CallBackListener;
import br.usp.ime.rusp.core.RemoteComment;
import br.usp.ime.rusp.core.actions.SendCommentAction;
import br.usp.ime.rusp.enumerations.QueueSize;
import br.usp.ime.rusp.enumerations.RU;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class SendCommentActivity extends RUSPBasicActivity {

	private Spinner ruSpinner = null;
	private Spinner queueSpinner = null;
	private EditText editText = null;
	private RemoteComment comment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_send_comment);

        queueSpinner = (Spinner) findViewById(R.id.spinner_test_queue);
        ArrayAdapter<QueueSize> queueAdapter = new ArrayAdapter<QueueSize>(this,
                android.R.layout.simple_spinner_dropdown_item, QueueSize.values());
        queueSpinner.setAdapter(queueAdapter);
        
        ruSpinner = (Spinner) findViewById(R.id.spinner_test_ru);
        ArrayAdapter<RU> ruAdapter = new ArrayAdapter<RU>(this,
                android.R.layout.simple_spinner_dropdown_item, RU.values());
        ruSpinner.setAdapter(ruAdapter);
        
        editText = (EditText) findViewById(R.id.edittext_send_comment);
        
        final Button button = (Button) findViewById(R.id.send_comment_button);
        button.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
                
            	String text = editText.getText().toString();
            	String ru = ruSpinner.getSelectedItem().toString();
            	String queue = queueSpinner.getSelectedItem().toString();
            	if ( text != null && text.length() > 0 && 
            			ru != null && ru.length() > 0 && 
            					queue != null && queue.length() > 0 ) {
            		
            		comment = new RemoteComment();
            		comment.texto = text;
            		comment.ru = RU.getRUByFriendlyName(ru);
            		comment.fila = QueueSize.getQueueByFriendlyName(queue);
            		
            	}
            	
            	if (SendCommentActivity.this.comment != null) {
            	
            		SendCommentAction action = new SendCommentAction( SendCommentActivity.this , null, 
            					new LocalCallBack(SendCommentActivity.this) );
                	action.send(SendCommentActivity.this.comment);
            		
            	}
            	else {
            		
            		Toast.makeText(SendCommentActivity.this, 
            				"Preencha os campos antes de enviar o coment�rio", 
            				Toast.LENGTH_LONG);
            		
            	}
            	
            }
        }
        );
        
	}
	
	private class LocalCallBack implements CallBackListener {

		private final SendCommentActivity owner;
		
		public LocalCallBack(SendCommentActivity owner) {
			this.owner = owner;
		}
		
		@Override
		public void beforeStart() {}

		@Override
		public void afterEnd() {}

		@Override
		public void sucess() {
			owner.editText.setText("");
        	queueSpinner.setSelection(0);        	
		}

		@Override
		public void fail(Exception e) {}
		
	}

	@Override
	public void update() {}
	
}
