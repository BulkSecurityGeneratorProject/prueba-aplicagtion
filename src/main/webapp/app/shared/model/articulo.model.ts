import { IProveedor } from 'app/shared/model/proveedor.model';

export interface IArticulo {
    id?: number;
    codigo?: string;
    descripcion?: string;
    ubicacion?: string;
    unidad?: string;
    valorUnidad?: number;
    idProveedor?: string;
    proveedor?: IProveedor;
}

export class Articulo implements IArticulo {
    constructor(
        public id?: number,
        public codigo?: string,
        public descripcion?: string,
        public ubicacion?: string,
        public unidad?: string,
        public valorUnidad?: number,
        public idProveedor?: string,
        public proveedor?: IProveedor
    ) {}
}
