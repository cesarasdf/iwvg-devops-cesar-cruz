package es.upm.miw.devops.code;

/**
 * DTO used to update a {@link User} through the REST API.
 * It only exposes the fields that a client is allowed to modify,
 * avoiding binding the persistent {@link User} entity directly to a request body.
 */
public record UserUpdateRequest(String name, String familyName, String email, String identity, String address,
                                 String city, String province, String postalCode, boolean active) {
}
