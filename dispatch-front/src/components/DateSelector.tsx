import { useEffect, useState } from "react";
import { dispatchApi } from "../api/dispatchApi";
import type { AvailableDate } from "../types/api";

interface Props {
    postalCode: string;
    onSelect: (date: string) => void;
}

export default function DateSelector({ postalCode, onSelect }: Props) {
    const [dates, setDates] = useState<AvailableDate[]>([]);

    const formatLabel = (date: string) => { 
        const today = new Date().toISOString().split("T")[0]; 
        if (date === today) return "Hoy"; 
        return date.split("-").slice(1).reverse().join("/"); 
    };

    useEffect(() => {
        dispatchApi
            .get(`/availability/dates?postalCode=${postalCode}`)
            .then((res) => setDates(res.data));
    }, [postalCode]);

    return (
        <div>
            <h3>Selecciona una fecha</h3>

            <div style={{ display: "flex", gap: 8, overflowX: "auto" }}>
                {dates.map((d) => (
                    <button
                        key={d.date}
                        onClick={() => onSelect(d.date)}
                        style={{
                            minWidth: 90,
                            padding: 12,
                            borderRadius: 8,
                            border: "1px solid #ccc",
                            background: "#fff",
                            cursor: "pointer",
                            color: "black"
                        }}
                    >
                        <strong>{formatLabel(d.date)}</strong>
                        <div style={{ fontSize: 12 }}>{d.date}</div>
                    </button>
                ))}
            </div>
        </div>
    );

}
