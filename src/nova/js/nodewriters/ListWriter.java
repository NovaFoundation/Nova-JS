package nova.js.nodewriters;

import net.fathomsoft.nova.tree.List;

public abstract class ListWriter extends NodeWriter
{
	public abstract List node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		node().forEachVisibleChild(child -> {
			getWriter(child).write(builder);
		});
		
		return builder;
	}
}