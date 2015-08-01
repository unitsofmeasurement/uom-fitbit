package tec.uom.client.fitbit.jackson.device;

import java.io.IOException;

import tec.units.ri.quantity.Quantities;
import tec.uom.client.fitbit.jackson.user.UserInfoDeserializer;
import tec.uom.client.fitbit.model.device.Device;
import tec.uom.client.fitbit.model.device.DeviceType;
import tec.uom.client.fitbit.model.units.UnitSystem;
import tec.uom.client.fitbit.model.user.UserInfo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * User: Anakar 
 * Date: 06/15/15
 */
public class DeviceDeserializer extends JsonDeserializer<Device> {

	@Override
	public Device deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode data = jp.readValueAsTree();
		UserInfo userInfo = null;
		if (data.has("user")) {
			UserInfoDeserializer userInfoDeserializer = new UserInfoDeserializer();
			userInfo = userInfoDeserializer.deserialize(jp, ctxt);
		}
		Device device = new Device(data.get("id").asText(),
				DeviceType.valueOf(data.get("type").asText()), data.get(
						"battery").asText(), Quantities.getQuantity(
						data.get("lastSyncTime").numberValue(), UnitSystem
								.getUnitSystem(userInfo.getLocale())
								.getDurationUnits().getUnitRepresentation()),
				data.get("deviceVersion").asText());
		return device;
	}
}
