package com.vftlite.parse.wrapper;

import com.coremedia.iso.boxes.threegpp26244.LocationInformationBox;

public class LocationInformationBoxWrapper {

	private LocationInformationBox loci;
	
	public LocationInformationBoxWrapper(LocationInformationBox box) {
		this.loci = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("role=").append(this.loci.getRole());
		result.append(";");
		result.append("longitude=").append(this.loci.getLongitude());
		result.append(";");
		result.append("latitude=").append(this.loci.getLatitude());
		result.append(";");
		result.append("altitude=").append(this.loci.getAltitude());
		result.append(";");
		result.append("astronomicalBody=").append(this.loci.getAstronomicalBody());
		result.append(";");
		result.append("additionalNotes=").append(this.loci.getAdditionalNotes());
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
}
