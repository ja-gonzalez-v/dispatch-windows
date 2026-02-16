import { dispatchApi } from "../api/dispatchApi";

interface Props {
    postalCode: string;
    timeSlotId: number;
    orderId: string;
    onSuccess: () => void;
    onError: (message: string) => void;
}



export default function ReservationSummary({
    postalCode,
    timeSlotId,
    orderId,
    onSuccess,
    onError
}: Props) {
    const confirm = async () => {
        try {
            await dispatchApi.post("/reservations", {
                orderId,
                addressLine: "Av. Siempre Viva 742",
                postalCode,
                timeSlotId,
            });

            onSuccess();
        } catch (err: any) {
            if (err.response?.status === 409) {
                onError("Este horario ya fue reservado por otro cliente");
            } else {
                onError("Ocurrió un error al confirmar la reserva");
            }
        }
    };

    return (
        <div style={{ marginTop: 24 }}>
            <button
                onClick={confirm}
                style={{
                    width: "100%",
                    padding: 16,
                    backgroundColor: "#007bff",
                    color: "white",
                    fontSize: 16,
                    borderRadius: 8,
                    border: "none",
                }}
            >
                Reservar
            </button>
        </div>
    );

}
