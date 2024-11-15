# Actualizar datos de ejemplo para "Terceros" con los campos obligatorios adicionales
terceros_data_updated = {
    "Nombre": ["Empresa A", "Empresa B", "Empresa C"],
    "Tipo de Tercero": ["Cliente", "Proveedor", "Cliente"],
    "Dirección": ["Calle 1", "Calle 2", "Calle 3"],
    "Código Postal": [12345, 67890, 54321],
    "Teléfono": ["555-1234", "555-5678", "555-9012"],
    "Email": ["empresaA@email.com", "empresaB@email.com", "empresaC@email.com"],
    "Estado": [1, 1, 1],         # Todos activos
    "Cliente": [1, 0, 1],        # Clientes marcados como 1, proveedores como 0
    "Proveedor": [0, 1, 0]       # Proveedores marcados como 1, clientes como 0
}

# Crear DataFrame actualizado para Terceros y guardarlo como CSV
terceros_df_updated = pd.DataFrame(terceros_data_updated)
terceros_file_path_updated = "/mnt/data/terceros_actualizado.csv"
terceros_df_updated.to_csv(terceros_file_path_updated, index=False)

terceros_file_path_updated
