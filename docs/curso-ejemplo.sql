-- Ejecutar manualmente en la base de desarrollo después de arrancar la aplicación.
-- No se carga automáticamente. Sustituye estos datos por los cursos reales.
INSERT INTO cursos (titulo, descripcion, categoria, nivel, duracion_horas, publicado)
SELECT 'Introducción a Java',
       'Aprende variables, estructuras de control y programación orientada a objetos con ejercicios prácticos.',
       'Programación', 'Inicial', 20, TRUE
WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE titulo = 'Introducción a Java');
