<template>
  <div>
    <nav id="navbar">
      <img src="..\..\./public/Logo.jpg" alt="Atom">
      <h1>Quantum Inventory</h1>
    </nav>
    <button @click="mostrarFormularioCrear">Crear Producto</button>
    
    <table id="productosTable" class="display">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Categoría</th>
          <th>Precio$</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="producto in productos" :key="producto.id">
          <td>{{ producto.titulo }}</td>
          <td>{{ producto.categoria }}</td>
          <td>{{ producto.precio }}</td>
          <td>
            <button @click="mostrarFormularioEditar(producto)">Editar</button>
            <button @click="eliminarProducto(producto.id)">Eliminar</button>
            <button @click="mostrarDetallesProducto(producto)">Detalles</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Formulario para Crear/Editar Producto -->
    <div v-if="mostrarFormulario" class="modal-overlay">
      <div class="modal-content">
        <h2>{{ editando ? 'Editar Producto' : 'Crear Producto' }}</h2>
        <span class="close" @click="cerrarFormulario">&times;</span>
        <form @submit.prevent="editando ? actualizarProducto() : crearProducto()" id="form-form"
          style="box-shadow: 0 2px 5px rgba(0, 0, 0, 0)">
          <!-- Campos del formulario -->
          <div>
            <label for="titulo">Título:</label>
            <input type="text" v-model="productoForm.titulo" required>
          </div>

          <div>
            <label for="descripcion">Descripción:</label>
            <textarea v-model="productoForm.descripcion" required></textarea>
          </div>

          <div>
            <label for="categoria">Categoría:</label>
            <select v-model="productoForm.categoria" required>
              <option v-for="categoria in categorias" :key="categoria" :value="categoria">{{ categoria }}</option>
            </select>
            <button type="button" @click="mostrarFormularioCrearCategoria" id="categoria-b">Crear Categoría</button>
          </div>

          <div>
            <label for="precio">Precio:</label>
            <input type="number" v-model="productoForm.precio" required @input="validarNumeroPositivo('precio')"
              step="any">
          </div>

          <div>
            <label for="descuento">Descuento (%):</label>
            <input type="number" v-model="productoForm.descuento" min="0" max="100" required @input="validarDescuento"
              step="any">
          </div>

          <div>
            <label for="stock">Stock:</label>
            <input type="number" v-model="productoForm.stock" required @input="validarNumeroPositivo('stock')">
          </div>

          <div>
            <label for="estadoDisponibilidad">Estado de Disponibilidad:</label>
            <input type="text" v-model="productoForm.estadoDisponibilidad" id="estadoDisponibilidad" readonly />
          </div>

          <div>
            <label for="marca">Marca:</label>
            <input type="text" v-model="productoForm.marca" required>
          </div>

          <div>
            <label for="sku">SKU:</label>
            <input type="text" v-model="productoForm.sku" required>
          </div>

          <div>
            <label for="peso">Peso:</label>
            <input type="number" v-model="productoForm.peso" required @input="validarNumeroPositivo('peso')" step="any">
          </div>

          <div>
            <label for="width">Ancho:</label>
            <input type="number" v-model="productoForm.width" required @input="validarNumeroPositivo('width')"
              step="any">
          </div>

          <div>
            <label for="height">Alto:</label>
            <input type="number" v-model="productoForm.height" required @input="validarNumeroPositivo('height')"
              step="any">
          </div>

          <div>
            <label for="depth">Profundidad:</label>
            <input type="number" v-model="productoForm.depth" required @input="validarNumeroPositivo('depth')"
              step="any">
          </div>

          <div>
            <label for="informacionGarantia">Información de Garantía:</label>
            <input type="text" v-model="productoForm.informacionGarantia" required
              @input="validarNumeroPositivo('informacionGarantia')">
          </div>

          <div>
            <label for="informacionEnvio">Información de Envío:</label>
            <input type="text" v-model="productoForm.informacionEnvio" required
              @input="validarNumeroPositivo('informacionEnvio')">
          </div>

          <div>
            <label for="politicaDevolucion">Política de Retorno:</label>
            <input type="text" v-model="productoForm.politicaDevolucion" required
              @input="validarNumeroPositivo('politicaDevolucion')">
          </div>

          <div>
            <label for="cantidadMinimaPedido">Cantidad Mínima de Pedido:</label>
            <input type="number" v-model="productoForm.cantidadMinimaPedido" required
              @input="validarNumeroPositivo('cantidadMinimaPedido')">
          </div>

          <div>
            <label for="imagen">Imagen:</label>
            <input type="text" v-model="productoForm.imagen" />
          </div>

          <div>
            <label for="tags">Tags:</label>
            <input type="text" v-model="nuevoTag">
            <button type="button" @click="agregarTag" id="categoria-b">Agregar Tag</button>
            <ul>
              <li v-for="(tag, index) in productoForm.tags" :key="index">
                {{ tag }} <button type="button" @click="eliminarTag(index)">x</button>
              </li>
            </ul>
          </div>
          <div id="tag">
            <label for="qrcode">QR Code:</label>
            <input type="text" v-model="productoForm.qrcode" />
          </div>

          <button type="submit">{{ editando ? 'Actualizar' : 'Crear' }}</button>
          <button type="button" @click="cancelarFormulario">Cancelar</button>

        </form>

        <div v-if="productoForm.barcode">
          <h3>Código de Barras</h3>
          <svg id="barcode"></svg>
        </div>
        <div v-if="mostrarFormularioCategoria">
          <h2>Crear Categoría</h2>
          <form @submit.prevent="crearCategoria">
            <label for="nuevaCategoria">Nueva Categoría:</label>
            <input type="text" v-model="nuevaCategoria" required>
            <button type="submit">Crear</button>
          </form>
        </div>
      </div>
    </div>

    <!-- Modal para mostrar detalles del producto -->
    <!-- Modal para mostrar detalles del producto -->
    <div v-if="mostrarDetalles" class="modal-overlay">
      <div class="modal-content">
        <span class="close" @click="cerrarDetalles">&times;</span>
        <h2>Detalles del Producto</h2>
        <p><strong>Id:</strong> {{ productoDetalles.id }}</p>
        <p><strong>Título:</strong> {{ productoDetalles.titulo }}</p>
        <p><strong>Descripción:</strong> {{ productoDetalles.descripcion }}</p>
        <p><strong>Categoría:</strong> {{ productoDetalles.categoria }}</p>
        <p><strong>Precio:</strong> {{ productoDetalles.precio }}$</p>
        <p><strong>Descuento:</strong> {{ productoDetalles.descuento }}%</p>
        <p><strong>Stock:</strong> {{ productoDetalles.stock }}</p>
        <p><strong>Estado Disponibilidad:</strong> {{ productoDetalles.estadoDisponibilidad }}</p>
        <p><strong>Marca:</strong> {{ productoDetalles.marca }}</p>
        <p><strong>SKU:</strong> {{ productoDetalles.sku }}</p>
        <p><strong>Peso:</strong> {{ productoDetalles.peso }}</p>
        <p><strong>Información de Garantía:</strong> {{ productoDetalles.informacionGarantia }} Dias</p>
        <p><strong>Envío en:</strong> {{ productoDetalles.informacionEnvio }} Dias</p>
        <p><strong>Política de Retorno:</strong> {{ productoDetalles.politicaDevolucion }} Dias</p>
        <p><strong>Cantidad Mínima de Pedido:</strong> {{ productoDetalles.cantidadMinimaPedido }}</p>
        <p><strong>Tags:</strong> {{ productoDetalles.tags.join(', ') }}</p>
        <p><strong>Ancho:</strong> {{ productoDetalles.width }}</p>
        <p><strong>Alto:</strong> {{ productoDetalles.height }}</p>
        <p><strong>Profundidad:</strong> {{ productoDetalles.depth }}</p>
        <div v-if="productoDetalles.imagen">
          <p><strong>Imagen (URL):</strong></p>
          <img :src="productoDetalles.imagen" alt="Imagen del Producto" />
        </div>
        <div v-if="productoDetalles.qrcode">
          <h3>QR Code</h3>
          <canvas id="detalleQRCode"></canvas>
        </div>
        <div v-if="productoDetalles.barcode">
          <h3>Código de Barras</h3>
          <svg id="detalleBarcode"></svg>
        </div>
      </div>
    </div>


    <!-- Formulario para Crear Categoría -->
  </div>
</template>

<script>
import axios from 'axios';
import $ from 'jquery';
import 'datatables.net';
import JsBarcode from 'jsbarcode';
import QRCode from 'qrcode';

export default {
  data() {
    return {
      productos: [],
      categorias: ['Electrónica', 'Ropa', 'Hogar',],
      mostrarFormulario: false,
      mostrarFormularioCategoria: false,
      mostrarDetalles: false,
      editando: false,
      productoForm: {
        id: null,
        titulo: '',
        descripcion: '',
        categoria: '',
        precio: null,
        descuento: null,
        stock: null,
        marca: '',
        sku: '',
        peso: '',
        informacionGarantia: null,
        informacionEnvio: null,
        politicaDevolucion: null,
        cantidadMinimaPedido: null,
        tags: [],
        fechaCreacion: '',
        fechaActualizacion: '',
        barcode: '',
        qrcode: '',
        width: null,
        height: null,
        depth: null,
        estadoDisponibilidad: '',
        imagen: '',
      },
      productoDetalles: {},
      nuevaCategoria: '',
      nuevoTag: '',
    };
  },
  watch: {
    'productoForm.stock': function (newStock) {
      this.productoForm.estadoDisponibilidad = newStock < 5 ? 'Bajo Stock' : 'En Stock';
    }
  },
  created() {
    this.obtenerProductos();
  },

  methods: {
    obtenerProductos() {
      axios.get('http://localhost:8082/productos')
        .then(response => {
          if ($.fn.dataTable.isDataTable('#productosTable')) {
          $('#productosTable').DataTable().destroy(); // Destruye la instancia existente de DataTable
        }

          this.productos = response.data;
          //definimos el datatable importante para que funcione
          this.$nextTick(function () {
            $('#productosTable').DataTable({
              paging: true,
              searching: true,
              ordering: true,
              info: true,
              lengthChange: true,
              pageLength: 10,
            });
          });
        })
        .catch(error => {
          console.error("Hubo un error al obtener los productos:", error);
        });
    },
    mostrarFormularioCrear() {
      this.mostrarFormulario = true;
      this.editando = false;
      this.resetForm();
    },
    mostrarFormularioEditar(producto) {
      this.mostrarFormulario = true;
      this.editando = true;
      this.productoForm = { ...producto };
      this.$nextTick(() => {
        if (this.productoForm.barcode) {
          this.generarCodigoBarras();
        }
      });
    },
    cancelarFormulario() {
      this.mostrarFormulario = false;
    },
    crearProducto() {
      const uniqueQRCode = this.generarQRCodeUnico();
      const baseURL = "https://tumama/";
      this.productoForm.qrcode = baseURL + uniqueQRCode;

      this.productoForm.barcode = this.generarCodigoBarrasUnico();
      axios.post('http://localhost:8082/productos', this.productoForm)
        .then(() => {
          this.obtenerProductos();
          this.mostrarFormulario = false;
        })
        .catch(error => {
          console.error("Hubo un error al crear el producto:", error.response ? error.response.data : error.message);
        });
      window.location.reload();
    },
    actualizarProducto() {
      axios.put(`http://localhost:8082/productos/${this.productoForm.id}`, this.productoForm)
        .then(() => {
          this.obtenerProductos();
          this.mostrarFormulario = false;
        })
        .catch(error => {
          console.error("Hubo un error al actualizar el producto:", error.response ? error.response.data : error.message);
        });
    },
    eliminarProducto(id) {
      axios.delete(`http://localhost:8082/productos/${id}`)
        .then(() => {
          this.obtenerProductos();
        })
        .catch(error => {
          console.error("Hubo un error al eliminar el producto:", error);
        });
    },
    mostrarFormularioCrearCategoria() {
      this.mostrarFormularioCategoria = true;
    },
    crearCategoria() {
      if (this.nuevaCategoria && !this.categorias.includes(this.nuevaCategoria)) {
        this.categorias.push(this.nuevaCategoria);
      }
      this.mostrarFormularioCategoria = false;
      this.nuevaCategoria = '';
    },
    agregarTag() {
      if (this.nuevoTag && !this.productoForm.tags.includes(this.nuevoTag)) {
        this.productoForm.tags.push(this.nuevoTag);
        this.nuevoTag = '';
      }
    },
    eliminarTag(index) {
      this.productoForm.tags.splice(index, 1);
    },
    resetForm() {
      this.productoForm = {
        id: null,
        titulo: '',
        descripcion: '',
        categoria: '',
        precio: null,
        descuento: null,
        stock: null,
        marca: '',
        sku: '',
        peso: null,
        informacionGarantia: null,
        informacionEnvio: null,
        politicaDevolucion: null,
        cantidadMinimaPedido: null,
        tags: [],
        fechaCreacion: '',
        fechaActualizacion: '',
        barcode: '',
        qrcode: '',
        width: null,
        height: null,
        depth: null,
        imagen: '',
      };
    },
    generarCodigoBarras() {
      if (this.productoForm.barcode) {
        JsBarcode("#barcode", this.productoForm.barcode, {
          format: "CODE128",
          displayValue: true
        });
      }
    },

    generarQRCode(data) {
      return QRCode.toCanvas(document.getElementById('detalleQRCode'), data, { errorCorrectionLevel: 'H' });
    },
    generarQRCodeUnico() {
      const uniqueQRCode = Math.floor(1000000000000 + Math.random() * 9000000000000).toString();
      return uniqueQRCode;
    },
    generarCodigoBarrasUnico() {
      return Math.floor(1000000000000 + Math.random() * 9000000000000).toString();
    },

    validarDescuento() {
      if (this.productoForm.descuento < 0) {
        this.productoForm.descuento = 0;
      } else if (this.productoForm.descuento > 100) {
        this.productoForm.descuento = 100;
      }
    },
    mostrarDetallesProducto(producto) {
      this.productoDetalles = { ...producto };
      this.mostrarDetalles = true;
      this.$nextTick(() => {
        if (this.productoDetalles.barcode) {
          JsBarcode("#detalleBarcode", this.productoDetalles.barcode, {
            format: "CODE128",
            displayValue: true
          });
        }
        if (this.productoDetalles.qrcode) {
          this.generarQRCode(this.productoDetalles.qrcode)
            .then(url => {
              this.productoDetalles.qrcode = url;
            })
            .catch(err => {
              console.error("Hubo un error al generar el QR code:", err);
            });
        }
      });
    },
    validarNumeroPositivo(campo) {
      if (this.productoForm[campo] < 0) {
        this.productoForm[campo] = 0;
      }
    },
    cerrarDetalles() {
      this.mostrarDetalles = false;
    },
    cerrarFormulario() {
      this.mostrarFormulario = false;
    }
  }
};
</script>
<style scoped>
/* Botones */
h1 {
  color: white;
  padding: 0;
}

.btn {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
  margin: 5px;
  transition: all 0.3s ease;
}

.btn-crear {
  background-color: #28a745;
  color: white;
}

.btn-editar {
  background-color: #17a2b8;
  color: white;
}

.modal-content img{
  width:50%;
}

.btn-eliminar {
  background-color: #dc3545;
  color: white;
}

.btn-detalles {
  background-color: #007bff;
  color: white;
}

#form-form {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  padding: 0;
  border-radius: 0;
  box-shadow: 0;
}

.btn:hover {
  transform: scale(1.05);
  opacity: 0.9;
}

.btn-guardar {
  background-color: #007bff;
  color: white;
}

.btn-cancelar {
  background-color: #6c757d;
  color: white;
}

.btn-cerrar {
  background-color: #dc3545;
  color: white;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 98%;
  max-width: 600px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  position: relative;
  animation: fadeIn 0.3s ease-out;

  /* Agregado: Hacer que el contenido tenga scroll */
  max-height: 80vh;
  /* Limita la altura del modal al 80% de la ventana */
  overflow-y: auto;
  /* Habilita el scroll vertical */
}

.modal-content h2 {
  margin-top: 0;
  color: #333;
}

.modal-content p {
  margin: 10px 0;
}

.close {
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 24px;
  color: #333;
  cursor: pointer;
}

.close:hover {
  color: #dc3545;
}

/* Animación */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }

  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>