package comm;

public record SentPulse(String sourceModule, Pulse pulse, String destinationModule) {
}
