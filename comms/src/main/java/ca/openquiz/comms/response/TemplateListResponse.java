package ca.openquiz.comms.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import ca.openquiz.comms.model.Template;

@JsonRootName("templates")
public class TemplateListResponse  extends BaseResponse 
{	
	@JsonProperty("template")
	List<Template> templates;
	
	public List<Template> getTemplates() {
		return templates;
	}

}
