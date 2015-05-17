package com.mag.boikov.testapp.communications;

import com.mag.boikov.testapp.R;

public enum Complaint {
    POOR_SOUND_QUALITY,
    CANT_HEAR_OTHER_PARTY,
    OTHER_PARTY_CANT_HEAR_ME,
    SLOW_INTERNET_CONNECTION,
    CALL_GETS_INTERRUPTED;

    public static Complaint valueOf(int complaintId) {
        switch (complaintId) {
            case R.id.poorSoundQuality:
                return Complaint.POOR_SOUND_QUALITY;
            case R.id.slowInternetConnection:
                return Complaint.SLOW_INTERNET_CONNECTION;
            case R.id.otherPartyCantHearMe:
                return Complaint.OTHER_PARTY_CANT_HEAR_ME;
            case R.id.cantHearOtherParty:
                return Complaint.CANT_HEAR_OTHER_PARTY;
            case R.id.callGetsInterrupted:
                return Complaint.CALL_GETS_INTERRUPTED;
            default:
                throw new RuntimeException("No mapping for id: " + complaintId);
        }
    }
}
