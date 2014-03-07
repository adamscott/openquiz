package ca.openquiz.comms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("groups")
public class GroupWrapper
{
	@JsonProperty("group")
	List<Group> groupList;

	public List<Group> getGroups()
	{
		return groupList;
	}
}
