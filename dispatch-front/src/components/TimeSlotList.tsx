import { useEffect, useState } from "react";
import { dispatchApi } from "../api/dispatchApi";
import type { TimeSlot } from "../types/api";

interface Props {
    postalCode: string;
    date: string;
    orderId: string;
    refreshKey: number;
    onSelect: (id: number) => void;
}



export default function TimeSlotList({ postalCode, date, orderId, refreshKey, onSelect }: Props) {
    const [slots, setSlots] = useState<TimeSlot[]>([]);
    const [selectedId, setSelectedId] = useState<number | null>(null);

    useEffect(() => {
        dispatchApi
            .get(`/availability/timeslots?postalCode=${postalCode}&date=${date}`)
            .then((res) => {
                setSlots(res.data);
                setSelectedId(null);
            });
    }, [postalCode, date, refreshKey]);


    const selectSlot = (id: number) => {
        dispatchApi.post(`/timeslots/${id}/select`, {
            orderId,
        });

        setSelectedId(id);
        onSelect(id);
    };


    return (
        <div>
            <h3>Horarios disponibles</h3>

            <div style={{ display: "grid", gap: 12 }}>
                {slots.map((slot) => (
                    <button
                        key={slot.id}
                        disabled={slot.remainingCapacity === 0}
                        onClick={() => selectSlot(slot.id)}
                        style={{
                            padding: 16,
                            borderRadius: 8,
                            border: "1px solid #ccc",
                            background:
                                selectedId === slot.id ? "#e6f0ff" : "#fff",
                            opacity: slot.remainingCapacity === 0 ? 0.4 : 1,
                            cursor: "pointer",
                            textAlign: "left",
                            color: "black"
                        }}
                    >
                        <div style={{ fontWeight: 600 }}>
                            {slot.startTime} – {slot.endTime}
                        </div>
                        <div>${slot.price}</div>
                    </button>
                ))}
            </div>
        </div>
    );

}
