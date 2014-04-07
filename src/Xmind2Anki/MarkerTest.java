package Xmind2Anki;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.marker.IMarkerRef;

public class MarkerTest {

public static List<String> FindTopicWithMarker(ISheet sheet, List<String> topic_id, String marker){
	IWorkbook workbook=sheet.getOwnedWorkbook();
	/*IMarkerSheet markerSheet = workbook.getMarkerSheet();
	IMarker gp=markerSheet.findMarker("flag-green");
	IMarkerGroup markerGroup = markerSheet.findMarkerGroup("Flags");
	IMarker marker = markerGroup.getMarker("flag-green");
	String id=marker.getId();*/
	List<String> topicwithmarker=new ArrayList<String>();
	List<String> markers=new ArrayList<String>();
	for (int i=0;i<topic_id.size();i++){
		ITopic topic=workbook.findTopic(topic_id.get(i));
		Set<IMarkerRef> markerSet = topic.getMarkerRefs();
		Object[] array=markerSet.toArray();
		
		
		for (int j=0;j<array.length;j++){
			markers.add(((IMarkerRef) array[j]).getMarkerId());
			if (((IMarkerRef) array[j]).getMarkerId().equals(marker)){
				topicwithmarker.add(topic_id.get(i));
			}
				
		}
		
	}
	return topicwithmarker;


}

}
