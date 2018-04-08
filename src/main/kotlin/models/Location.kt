package models

data class Location (
    private val latitude : Float,
    private val longitude : Float,
    private val altitude : Float,
    private val bearing : Float,
    private val accuracy : Float
) {

    override fun toString(): String {
        return "Latitude: " + latitude.toString() +
               " Longitude: " + longitude.toString() +
               " Altitude: " + altitude.toString() +
               " Bearing: " + bearing.toString() +
               " Accuracy: " + accuracy.toString()
    }
}
