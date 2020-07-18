package com.github.luanneves07.ifood.registration.dto;

public class RestaurantDto {

	public String owner;

	public String cnpj;

	public String tradingName;

	public String creationTime;

	public LocationDto location;

	@Override
	public String toString() {
		return String.format(":::%s:::%n::<tradingName>%s::%n::<owner>%s::%n::<creationTime>%s::%n",
				this.getClass().getSimpleName(), tradingName, owner, creationTime);
	}
}
