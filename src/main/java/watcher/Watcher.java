package watcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * @author Munteanu Ion (imuntean@redhat.com) 
 *	
 *	xnio watcher test
 */
public class Watcher {

	public static void main(String[] args) {

		Path this_dir = getPath("/home/imuntean/desk");
		System.out.println("Now watching the" + this_dir.getParent() + " directory ...");

		try {
			WatchService watcher = this_dir.getFileSystem().newWatchService();
			this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey watckKey = watcher.take();

			List<WatchEvent<?>> events = watckKey.pollEvents();
			for (WatchEvent event : events) {
				if (event.kind().name().toString() == "ENTRY_CREATE") {

					System.out.println("Someone just created the file '" + event.context().toString() + "'.");
				} else if (event.kind().name().toString() == "ENTRY_MODIFY") {
					System.out.println("Modified " + event.context().toString());
				} else if (event.kind().name().toString() == "ENTRY_DELETE") {

					System.out.println("File deleted " + event.context().toString());
				}

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static Path getPath(String path) {
		return Paths.get(path);
	}
}
