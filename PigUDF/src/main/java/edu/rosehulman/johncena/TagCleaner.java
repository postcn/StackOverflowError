package edu.rosehulman.johncena;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

public class TagCleaner extends EvalFunc<DataBag> {
	static BagFactory mBagFactory = BagFactory.getInstance();
	static TupleFactory mTupleFactory = TupleFactory.getInstance();

	@Override
	public DataBag exec(Tuple arg0) throws IOException {
		if (arg0 == null || arg0.size() < 1) {
			return null;
		}
		try {
			DataBag output = mBagFactory.newDefaultBag();
			Object o = arg0.get(0);
			if (o == null) {
				return null;
			}
			if (!(o instanceof String)) {
				throw new IOException("Expected input type to be chararray but got " + o.getClass().getName());
			}
			String[] tags = ((String ) o).replace("<", "").split(">");
			for (String tag: tags) {
				output.add(mTupleFactory.newTuple(tag));
			}
			
			return output;
		}
		catch(ExecException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	public static void main(String[] args) throws IOException {
		String toSplit = "&lt;c#&gt;&lt;winforms&gt;&lt;type-conversion&gt;&lt;opacity&gt;";
		Tuple splitter = mTupleFactory.newTuple(toSplit);
		TagCleaner t = new TagCleaner();
		DataBag output = t.exec(splitter);
		System.out.println(output.toString());
		System.out.println(output.size());
	}
}
