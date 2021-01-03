package pl.latusikl.client.server;

@FunctionalInterface
public interface OutputSender {
	void manageOutput(String message);
}
