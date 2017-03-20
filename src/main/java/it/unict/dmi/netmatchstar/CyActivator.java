/*
 * Copyright (c) 2015, Rosalba Giugno.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the University of Catania nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ROSALBA GIUGNO ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ROSALBA GIUGNO BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package it.unict.dmi.netmatchstar;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.Properties;

import it.unict.dmi.netmatchstar.utils.Common;
import it.unict.dmi.netmatchstar.view.*;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

    private static final String APP_NAME = "NetMatch*";

    private CyApplicationManager cyApplicationManager;
    private CyServiceRegistrar cyServiceRegistrar;
    private CySwingAppAdapter cySwingAppAdapter;
    private CySwingApplication cySwingApplication;

    public CyActivator() {
        super();
    }

	@Override
	public void start(BundleContext bc) {
        cyApplicationManager = getService(bc, CyApplicationManager.class);
        cyServiceRegistrar = getService(bc, CyServiceRegistrar.class);
        cySwingAppAdapter = getService(bc, CySwingAppAdapter.class);
        cySwingApplication = getService(bc, CySwingApplication.class);

        MenuAction menuAction = new MenuAction(Common.APP_NAME, this, cySwingAppAdapter);
        registerAllServices(bc, menuAction, new Properties());
        //cySwingAppAdapter.getCySwingApplication().addAction(menuAction);

        //WestPanel westPanel = new WestPanel(cySwingAppAdapter);
        //WestPanelAction controlPanelAction = new WestPanelAction(cySwingApplication, westPanel);

        //registerService(bc, westPanel, CytoPanelComponent.class, new Properties());
        //registerService(bc, controlPanelAction, CyAction.class, new Properties());

        Properties editNodeLabelProps = new Properties();
        editNodeLabelProps.setProperty(PREFERRED_ACTION, "NEW");
        editNodeLabelProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
        editNodeLabelProps.setProperty(MENU_GRAVITY, "6.0f");
        editNodeLabelProps.setProperty(IN_MENU_BAR, "false");
        editNodeLabelProps.setProperty(TITLE, "Edit Node Label Attribute");

        cyServiceRegistrar.registerService(new EditNodeLabelTaskFactory(cySwingAppAdapter),
                NodeViewTaskFactory.class, editNodeLabelProps);

        Properties editEdgeLabelProps = new Properties();
        editEdgeLabelProps.setProperty(PREFERRED_ACTION, "NEW");
        editEdgeLabelProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
        editEdgeLabelProps.setProperty(MENU_GRAVITY, "6.0f");
        editEdgeLabelProps.setProperty(IN_MENU_BAR, "false");
        editEdgeLabelProps.setProperty(TITLE, "Edit Edge Label Attribute");

        cyServiceRegistrar.registerService(new EditEdgeLabelTaskFactory(cySwingAppAdapter),
                EdgeViewTaskFactory.class, editEdgeLabelProps);

        Properties editSetApproxPathProps = new Properties();
        editSetApproxPathProps.setProperty(PREFERRED_ACTION, "NEW");
        editSetApproxPathProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
        editSetApproxPathProps.setProperty(MENU_GRAVITY, "7.0f");
        editSetApproxPathProps.setProperty(IN_MENU_BAR, "false");
        editSetApproxPathProps.setProperty(TITLE, "Set Approximate Path");

        cyServiceRegistrar.registerService(new SetApproxPathTaskFactory(cySwingAppAdapter),
                EdgeViewTaskFactory.class, editSetApproxPathProps);
    }

    public CyServiceRegistrar getcyServiceRegistrar() {
        return cyServiceRegistrar;
    }

    public CySwingAppAdapter getCySwingAppAdapter() {
        return cySwingAppAdapter;
    }

    public CySwingApplication getCySwingApplication() {
        return cySwingApplication;
    }

    public CyApplicationManager getcyApplicationManager() {
        return cyApplicationManager;
    }

	/*public NetMatchApp(CySwingAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication()
        		.addAction(new MenuAction(adapter));

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();

		Properties editNodeLabelProps = new Properties();
		editNodeLabelProps.setProperty(PREFERRED_ACTION, "NEW");
		editNodeLabelProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
		editNodeLabelProps.setProperty(MENU_GRAVITY, "6.0f");
		editNodeLabelProps.setProperty(IN_MENU_BAR, "false");
		editNodeLabelProps.setProperty(TITLE, "Edit Node Label Attribute");

	  	registrar.registerService(new EditNodeLabelTaskFactory(adapter),
	  			NodeViewTaskFactory.class, editNodeLabelProps);

	  	Properties editEdgeLabelProps = new Properties();
	  	editEdgeLabelProps.setProperty(PREFERRED_ACTION, "NEW");
		editEdgeLabelProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
		editEdgeLabelProps.setProperty(MENU_GRAVITY, "6.0f");
		editEdgeLabelProps.setProperty(IN_MENU_BAR, "false");
		editEdgeLabelProps.setProperty(TITLE, "Edit Edge Label Attribute");

	  	registrar.registerService(new EditEdgeLabelTaskFactory(adapter),
	  			EdgeViewTaskFactory.class, editEdgeLabelProps);

	  	Properties editSetApproxPathProps = new Properties();
	  	editSetApproxPathProps.setProperty(PREFERRED_ACTION, "NEW");
	  	editSetApproxPathProps.setProperty(PREFERRED_MENU, "NetMatch*[100]");
	  	editSetApproxPathProps.setProperty(MENU_GRAVITY, "7.0f");
	  	editSetApproxPathProps.setProperty(IN_MENU_BAR, "false");
	  	editSetApproxPathProps.setProperty(TITLE, "Set Approximate Path");

	  	registrar.registerService(new SetApproxPathTaskFactory(adapter),
	  			EdgeViewTaskFactory.class, editSetApproxPathProps);
	}*/
}