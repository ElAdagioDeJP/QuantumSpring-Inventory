<template>
  <div>
    <h1>CRUD de Productos</h1>
    <button @click="mostrarFormularioCrear">Crear Producto</button>
    <table id="productosTable" class="display">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Categoría</th>
          <th>Precio</th>
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
    <div v-if="mostrarFormulario">
      <h2>{{ editando ? 'Editar Producto' : 'Crear Producto' }}</h2>
      <form @submit.prevent="editando ? actualizarProducto() : crearProducto()">
        <!-- Campos del formulario -->
        <label for="titulo">Título:</label>
        <input type="text" v-model="productoForm.titulo" required>

        <label for="descripcion">Descripción:</label>
        <textarea v-model="productoForm.descripcion" required></textarea>

        <label for="categoria">Categoría:</label>
        <select v-model="productoForm.categoria" required>
          <option v-for="categoria in categorias" :key="categoria" :value="categoria">{{ categoria }}</option>
        </select>
        <button @click="mostrarFormularioCrearCategoria">Crear Categoría</button>

        <label for="precio">Precio:</label>
        <input type="number" v-model="productoForm.precio" required>

        <label for="descuento">Descuento (%):</label>
        <input type="number" v-model="productoForm.descuento" required>

        <label for="stock">Stock:</label>
        <input type="number" v-model="productoForm.stock" required>

        <label for="estadoDisponibilidad">Estado de Disponibilidad:</label>
        <input type="text" v-model="productoForm.estadoDisponibilidad" id="estadoDisponibilidad" readonly />

        <label for="marca">Marca:</label>
        <input type="text" v-model="productoForm.marca" required>

        <label for="sku">SKU:</label>
        <input type="text" v-model="productoForm.sku" required>

        <label for="peso">Peso:</label>
        <input type="number" v-model="productoForm.peso" required>

        <label for="width">Ancho:</label>
        <input type="number" v-model="productoForm.width" required/>
    
        <label for="height">Alto:</label>
        <input type="number" v-model="productoForm.height" required/>
    
        <label for="depth">Profundidad:</label>
        <input type="number" v-model="productoForm.depth" required/>

        <label for="informacionGarantia">Información de Garantía:</label>
        <input type="number" v-model="productoForm.informacionGarantia" required>

        <label for="informacionEnvio">Información de Envío:</label>
        <input type="number" v-model="productoForm.informacionEnvio" required>

        <label for="politicaDevolucion">Política de Retorno:</label>
        <input type="number" v-model="productoForm.politicaDevolucion" required>

        <label for="cantidadMinimaPedido">Cantidad Mínima de Pedido:</label>
        <input type="number" v-model="productoForm.cantidadMinimaPedido" required>

        <label for="imagen"> Imagen:</label>
        <input type="text" v-model="productoForm.imagen"  />

        <label for="tags">Tags:</label>
        <input type="text" v-model="nuevoTag">
        <button type="button" @click="agregarTag">Agregar Tag</button>
        <ul>
          <li v-for="(tag, index) in productoForm.tags" :key="index">
            {{ tag }} <button type="button" @click="eliminarTag(index)">x</button>
          </li>
        </ul>
        
        <button type="submit">{{ editando ? 'Actualizar' : 'Crear' }}</button>
        <button type="button" @click="cancelarFormulario">Cancelar</button>
      </form>

      <div v-if="productoForm.barcode">
        <h3>Código de Barras</h3>
        <svg id="barcode"></svg>
      </div>
    </div>

    <!-- Modal para mostrar detalles del producto -->
    <div v-if="mostrarDetalles" class="modal">
      <div class="modal-content">
        <span class="close" @click="cerrarDetalles">&times;</span>
        <h2>Detalles del Producto</h2>
        <p><strong>Id:</strong> {{ productoDetalles.id }}</p>
        <p><strong>Título:</strong> {{ productoDetalles.titulo }}</p>
        <p><strong>Descripción:</strong> {{ productoDetalles.descripcion }}</p>
        <p><strong>Categoría:</strong> {{ productoDetalles.categoria }}</p>
        <p><strong>Precio:</strong> {{ productoDetalles.precio }}</p>
        <p><strong>Descuento:</strong> {{ productoDetalles.descuento }}%</p>
        <p><strong>Stock:</strong> {{ productoDetalles.stock }}</p>
        <p><strong>Estado Disponibilidad:</strong> {{ productoDetalles.estadoDisponibilidad }}</p>
        <p><strong>Marca:</strong> {{ productoDetalles.marca }}</p>
        <p><strong>SKU:</strong> {{ productoDetalles.sku }}</p>
        <p><strong>Peso:</strong> {{ productoDetalles.peso }}</p>
        <p><strong>Información de Garantía:</strong> {{ productoDetalles.informacionGarantia }}</p>
        <p><strong>Información de Envío:</strong> {{ productoDetalles.informacionEnvio }}</p>
        <p><strong>Política de Retorno:</strong> {{ productoDetalles.politicaDevolucion }}</p>
        <p><strong>Cantidad Mínima de Pedido:</strong> {{ productoDetalles.cantidadMinimaPedido }}</p>
        <p><strong>Tags:</strong> {{ productoDetalles.tags.join(', ') }}</p>
        <p><strong>Ancho:</strong> {{ productoDetalles.width }}</p>
        <p><strong>Alto:</strong> {{ productoDetalles.height }}</p>
        <p><strong>Profundidad:</strong> {{ productoDetalles.depth }}</p>
        <div v-if="productoDetalles.imagen">
          <p><strong>Imagen(Url):</strong></p>
          <img :src="productoDetalles.imagen" alt="Imagen del Producto" />
        </div>
        <div v-if="productoDetalles.barcode">
          <h3>Código de Barras</h3>
          <svg id="detalleBarcode"></svg>
        </div>
      </div>
    </div>

    <!-- Formulario para Crear Categoría -->
    <div v-if="mostrarFormularioCategoria">
      <h2>Crear Categoría</h2>
      <form @submit.prevent="crearCategoria">
        <label for="nuevaCategoria">Nueva Categoría:</label>
        <input type="text" v-model="nuevaCategoria" required>
        <button type="submit">Crear</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import $ from 'jquery';
import 'datatables.net';
import JsBarcode from 'jsbarcode';

export default {
  data() {
    return {
      productos: [],
      categorias: ['Electrónica', 'Ropa', 'Hogar'],
      mostrarFormulario: false,
      mostrarFormularioCategoria: false,
      mostrarDetalles: false,
      editando: false,
      productoForm: {
        id: null,
        titulo: '',
        descripcion: '',
        categoria: '',
        precio: 0,
        descuento: 0,
        stock: 0,
        marca: '',
        sku: '',
        peso: 0,
        informacionGarantia: 0,
        informacionEnvio: 0,
        politicaDevolucion: 0,
        cantidadMinimaPedido: 0,
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
    'productoForm.stock': function(newStock) {
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
            $('#productosTable').DataTable().destroy();
          }
          this.productos = response.data;
          //definimos el datatable importante para que funcione
          this.$nextTick(function() {
            $('#productosTable').DataTable({
              paging: true,
              searching: true,
              ordering: true,
              info: true,
              lengthChange: true,
              pageLength: 10,
              language: {
                url: '//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json'
              }
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
      this.productoForm.barcode = this.generarCodigoBarrasUnico();
      axios.post('http://localhost:8082/productos', this.productoForm)
        .then(() => {
          this.obtenerProductos();
          this.mostrarFormulario = false;
        })
        .catch(error => {
          console.error("Hubo un error al crear el producto:", error.response ? error.response.data : error.message);
        });
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
        precio: 0,
        descuento: 0,
        stock: 0,
        marca: '',
        sku: '',
        peso: 0,
        informacionGarantia: 0,
        informacionEnvio: 0,
        politicaDevolucion: 0,
        cantidadMinimaPedido: 0,
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
    generarCodigoBarrasUnico() {
      return Math.floor(1000000000000 + Math.random() * 9000000000000).toString();
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
      });
    },
    cerrarDetalles() {
      this.mostrarDetalles = false;
    }
  }
};
</script>

