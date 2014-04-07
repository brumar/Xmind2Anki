package test_lock;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

/*
 * Created on Aug 17, 2005
 *
 */

/**
 * @author HackTrack
 *
 */
public class FormFillActionListener implements CaretListener {
	private Component trigger;
	private HashMap components;

	public FormFillActionListener() {
		super();
		trigger = null;
		components = new HashMap();
	}

	public void caretUpdate(CaretEvent ce) {
		JTextField jtf = (JTextField)ce.getSource();
		Boolean filledField = new Boolean(false);
		if (!jtf.getText().equals("")) {
			filledField = new Boolean(true);
		}
		components.put(jtf, filledField);

		boolean isFilled = true;
		Iterator it = components.keySet().iterator();
		while (it.hasNext()) {
			JTextComponent tc = (JTextComponent)it.next();
			if (((Boolean)components.get(tc)).booleanValue() == false) {
				isFilled = false;
				break;
			}
		}
			this.trigger.setEnabled(isFilled);

		JTextField comp = (JTextField)ce.getSource();
		if (comp.getText() != null && !comp.getText().equals("")) {
			components.put(comp, new Boolean("true"));
		} else {
			components.put(comp, new Boolean("false"));
		}
	}

	public void registerComponent(JTextField tf) {
		tf.addCaretListener(this);
		components.put(tf, new Boolean(false));

	}

	public void unregisterComponent(JTextField tf) {
		tf.removeCaretListener(this);
		components.remove(tf);
	}

	public void setTriggerComponent(Component c) {
		this.trigger = c;
		this.trigger.setEnabled(false);
	}

}
