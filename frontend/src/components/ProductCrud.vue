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
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Formulario para Crear/Editar Producto -->
    <div v-if="mostrarFormulario">
      <h2>{{ editando ? 'Editar Producto' : 'Crear Producto' }}</h2>
      <form @submit.prevent="editando ? actualizarProducto() : crearProducto()">
        <label for="titulo">Título:</label>
        <input type="text" v-model="productoForm.titulo" required>

        <label for="categoria">Categoría:</label>
        <input type="text" v-model="productoForm.categoria" required>

        <label for="precio">Precio:</label>
        <input type="number" v-model="productoForm.precio" required>

        <!-- Otros campos según sea necesario -->

        <button type="submit">{{ editando ? 'Actualizar' : 'Crear' }}</button>
        <button @click="cancelarFormulario">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import $ from 'jquery';
import 'datatables.net';

export default {
  data() {
    return {
      productos: [],
      mostrarFormulario: false,
      editando: false,
      productoForm: {
        id: null,
        titulo: '',
        categoria: '',
        precio: 0,
        // Otros campos según sea necesario
      }
    };
  },
  created() {
    this.obtenerProductos();
  },
  methods: {
    obtenerProductos() {
      axios.get('http://localhost:8081/productos')
        .then(response => {
          if ($.fn.dataTable.isDataTable('#productosTable')) {
            $('#productosTable').DataTable().destroy();
          }
          this.productos = response.data;
          this.$nextTick(function() {
            $('#productosTable').DataTable();
          });
        })
        .catch(error => {
          console.error("Hubo un error al obtener los productos:", error);
        });
    },
    mostrarFormularioCrear() {
      this.mostrarFormulario = true;
      this.editando = false;
      this.productoForm = {
        id: null,
        titulo: '',
        categoria: '',
        precio: 0,
        // Otros campos según sea necesario
      };
    },
    mostrarFormularioEditar(producto) {
      this.mostrarFormulario = true;
      this.editando = true;
      this.productoForm = { ...producto };
    },
    cancelarFormulario() {
      this.mostrarFormulario = false;
    },
    crearProducto() {
      axios.post('http://localhost:8081/productos', this.productoForm)
        .then(() => {
          this.obtenerProductos();
          this.mostrarFormulario = false;
        })
        .catch(error => {
          console.error("Hubo un error al crear el producto:", error);
        });
    },
    actualizarProducto() {
      axios.put(`http://localhost:8081/productos/${this.productoForm.id}`, this.productoForm)
        .then(() => {
          this.obtenerProductos();
          this.mostrarFormulario = false;
        })
        .catch(error => {
          console.error("Hubo un error al actualizar el producto:", error);
        });
    },
    eliminarProducto(id) {
      axios.delete(`http://localhost:8081/productos/${id}`)
        .then(() => {
          this.obtenerProductos();
        })
        .catch(error => {
          console.error("Hubo un error al eliminar el producto:", error);
        });
    }
  },
 
};
</script>

<style>
/* Estilos para la tabla */
#productosTable {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  font-size: 18px;
  text-align: left;
}

#productosTable thead tr {
  background-color: #009879;
  color: #ffffff;
  text-align: left;
  font-weight: bold;
}

#productosTable th,
#productosTable td {
  padding: 12px 15px;
}

#productosTable tbody tr {
  border-bottom: 1px solid #dddddd;
}

#productosTable tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

#productosTable tbody tr:last-of-type {
  border-bottom: 2px solid #009879;
}

#productosTable tbody tr.active-row {
  font-weight: bold;
  color: #009879;
}
</style>