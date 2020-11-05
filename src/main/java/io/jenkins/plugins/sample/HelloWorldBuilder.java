package io.jenkins.plugins.sample;

import java.io.IOException;

import javax.servlet.ServletException;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {
	private final String name;
	private boolean useFrench;

	@DataBoundConstructor
	public HelloWorldBuilder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isUseFrench() {
		return useFrench;
	}

	@DataBoundSetter
	public void setUseFrench(boolean useFrench) {
		this.useFrench = useFrench;
	}

	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
		run.addAction(new HelloWorldAction(name));
                System.out.println("Action.......");
		if (useFrench) {
			listener.getLogger().println("Bonjour, " + name + "!");
		} else {
			listener.getLogger().println("Hello, " + name + "!");
		}
	}

	@Symbol("greet")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

	    // validation for name	
            
            public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
				throws IOException, ServletException {
                System.out.println("Validation.......");
                    // do-validation
			return FormValidation.ok();
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}

		@Override
		public String getDisplayName() {
			return "Hello world";
		}
	}
}
