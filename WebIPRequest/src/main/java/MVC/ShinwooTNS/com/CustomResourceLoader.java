package MVC.ShinwooTNS.com;

import org.springframework.core.io.Resource;

public class CustomResourceLoader {

	private Resource[] resource;

	public Resource[] getResource() {
		return resource;
	}

	public void setResource(Resource[] resource) {
		this.resource = resource;
	}
}
