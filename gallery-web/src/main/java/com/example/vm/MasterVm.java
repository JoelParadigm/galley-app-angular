package com.example.vm;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class MasterVm {

    @Command
    public void navigateUpload() {
        Executions.sendRedirect("page.zul");
    }

    @Command
    public void navigateGallery() {
        Executions.sendRedirect("gallery.zul");
    }

    @Command
    public void navigateUnknown() {
        Executions.sendRedirect("/gallery/images");
    }

    @Command
    public void navigateDetail() {
        Executions.sendRedirect("imageDetail.zul");
    }
}
