import { useRef, useState } from "react";
import AddressForm from "../components/AddressForm";
import DateSelector from "../components/DateSelector";
import ReservationSummary from "../components/ReservationSummary";
import TimeSlotList from "../components/TimeSlotList";

export default function HomePage() {
    const orderIdRef = useRef<string>(crypto.randomUUID());

    const [postalCode, setPostalCode] = useState<string | null>(null);
    const [date, setDate] = useState<string | null>(null);
    const [timeSlotId, setTimeSlotId] = useState<number | null>(null);
    const [refreshKey, setRefreshKey] = useState(0);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);



    return (
        <div style={{ maxWidth: 600, margin: "0 auto", padding: 24 }}>
            <h2>Despacho a domicilio</h2>

            <AddressForm onSubmit={setPostalCode} />

            {postalCode && (
                <DateSelector postalCode={postalCode} onSelect={setDate} />
            )}

            {postalCode && date && (
                <TimeSlotList
                    postalCode={postalCode}
                    date={date}
                    orderId={orderIdRef.current}
                    refreshKey={refreshKey}
                    onSelect={setTimeSlotId}
                />
            )}

            {postalCode && date && timeSlotId && (
                <ReservationSummary
                    postalCode={postalCode}
                    timeSlotId={timeSlotId}
                    orderId={orderIdRef.current}
                    onSuccess={() => {
                        setSuccessMessage("Reserva confirmada exitosamente");
                        setErrorMessage(null);
                        setRefreshKey((k) => k + 1);
                    }}
                    onError={(message) => {
                        setErrorMessage(message);
                        setSuccessMessage(null);
                    }}
                />

            )}
            {successMessage && (
                <div style={{ marginTop: 16, color: "green" }}>
                    {successMessage}
                </div>
            )}

            {errorMessage && (
                <div style={{ marginTop: 16, color: "red" }}>
                    {errorMessage}
                </div>
            )}

        </div>
    );
}
