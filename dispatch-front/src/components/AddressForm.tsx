import { useState } from "react";

interface Props {
    onSubmit: (postalCode: string) => void;
}

export default function AddressForm({ onSubmit }: Props) {
    const [postalCode, setPostalCode] = useState("");

    return (
        <form
            onSubmit={(e) => {
                e.preventDefault();
                onSubmit(postalCode);
            }}
        >
            <input
                placeholder="Postal Code"
                value={postalCode}
                onChange={(e) => setPostalCode(e.target.value)}
            />
            <button type="submit">Buscar</button>
        </form>
    );
}
